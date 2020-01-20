package com.dao.sso.reactive.handler;

import com.dao.sso.reactive.handler.auth.CloudAuthHandler;
import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.dao.sso.reactive.service.BaseService;
import com.dao.sso.reactive.service.BusinessService;
import com.dao.sso.reactive.service.CacheService;
import com.dao.sso.reactive.service.CloudService;
import com.holderzone.framework.response.Result;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 17:42
 * @description
 */
public class CloudLoginHandler extends AbstractLoginHandler {

    private CloudAuthHandler cloudAuthHandler;

    private CacheService cacheService;

    private CloudService cloudService;

    private BaseService baseService;

    private static final Integer DISABLE_USER_LOGIN_COUNT = 10;

    private static final Integer VERIFY_CODE_LOGIN_COUNT = 3;

    public CloudLoginHandler(CloudAuthHandler cloudAuthHandler, CacheService cacheService,
                             BaseService baseService, CloudService cloudService, BusinessService businessService,
                             Boolean verifyEnable) {
        super(cacheService, businessService, verifyEnable);
        this.cacheService = cacheService;
        this.cloudService = cloudService;
        this.baseService = baseService;
        this.cloudAuthHandler = cloudAuthHandler;

    }

    @Override
    public Mono<AuthResultBO> auth(LoginUserInfoBO loginUserInfoBO) {
        return cloudAuthHandler.auth(loginUserInfoBO);
    }

    @Override
    public Mono<Void> handleAuthFailure(LoginUserInfoBO loginUserInfoBO, String reason) {
        Mono<Integer> loginCountMono = cacheService.incrementLoginCount(loginUserInfoBO.getUsername());
        return loginCountMono
                .flatMap(loginCount -> {
                    if (loginCount > DISABLE_USER_LOGIN_COUNT) {
                        return cloudService.disableUserAtLogin(loginUserInfoBO.getUsername());
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> isValidateVerifyCode(LoginUserInfoBO loginUserInfoBO) {
        Mono<Integer> loginCountMono = cacheService.selectLoginCount(loginUserInfoBO.getUsername());
        return loginCountMono.map(loginCount -> loginCount >= VERIFY_CODE_LOGIN_COUNT);
    }

    @Override
    public Mono<Boolean> verifyCodeValidate(LoginUserInfoBO loginUserInfoBO, Map<String, Object> userInfoMap) {
        return baseService.verifyCodeValid(loginUserInfoBO.getVid(), loginUserInfoBO.getvCode());
    }

    @Override
    public Mono<Result> afterLogin(LoginUserInfoBO loginUserInfoBO, Map<String, Object> userInfoMap) {
        Mono<Void> mono = cacheService.cleanLoginCount(loginUserInfoBO.getUsername());
        return mono.then(super.afterLogin(loginUserInfoBO, userInfoMap));
    }
}
