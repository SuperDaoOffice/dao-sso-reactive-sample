package com.dao.sso.reactive.handler;

import com.dao.sso.reactive.entity.TokenRequestBO;
import com.dao.sso.reactive.entity.dto.LoginDTO;
import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginTransform;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.dao.sso.reactive.service.BusinessService;
import com.dao.sso.reactive.service.CacheService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.holderzone.framework.response.Result;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author HuChiHui
 * @date 2019/11/22 下午 15:40
 * @description
 */
public abstract class AbstractLoginHandler {

    private Boolean verifyEnable;

    private CacheService cacheService;

    private BusinessService businessService;

    public LoginTransform loginTransform = LoginTransform.INSTANCE;

    public static final Executor executor = new ThreadPoolExecutor(1, 20,
            1L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("send-mq-message-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());


    public AbstractLoginHandler(CacheService cacheService, BusinessService businessService,
                                Boolean verifyEnable) {
        this.businessService = businessService;
        this.cacheService = cacheService;
        this.verifyEnable = verifyEnable;

    }

    /**
     * 登录
     *
     * @param loginUserInfoBO
     * @return
     */
    public abstract Mono<AuthResultBO> auth(LoginUserInfoBO loginUserInfoBO);

    /**
     * 处理失败
     *
     * @param loginUserInfoBO
     * @param reason
     */
    public Mono<Void> handleAuthFailure(LoginUserInfoBO loginUserInfoBO, String reason) {
        return Mono.empty();
    }

    /**
     * 是否需要验证码
     *
     * @param loginUserInfoBO
     * @return
     */
    public Mono<Boolean> isValidateVerifyCode(LoginUserInfoBO loginUserInfoBO) {
        return Mono.just(verifyEnable);
    }

    /**
     * 验证 验证码
     *
     * @param loginUserInfoBO
     * @param userInfoMap
     * @return
     */
    public Mono<Boolean> verifyCodeValidate(LoginUserInfoBO loginUserInfoBO, Map<String, Object> userInfoMap) {
        return Mono.just(true);
    }

    /**
     * 登录后的处理事项，并返回token
     *
     * @param loginUserInfo
     * @param selectedUserInfoMap
     * @return
     */
    public Mono<Result> afterLogin(LoginUserInfoBO loginUserInfo, Map<String, Object> selectedUserInfoMap) {
        executor.execute(() -> {
            LoginDTO loginDTO = loginTransform.userInfoBoToDto(loginUserInfo);
            businessService.sendMessage(loginDTO);
        });
        String enterpriseGuid = (String) selectedUserInfoMap.get("enterpriseGuid");
        String userGuid = (String) selectedUserInfoMap.get("userGuid");
        TokenRequestBO tokenRequestBO = new TokenRequestBO(enterpriseGuid, loginUserInfo.getStoreNo(), loginUserInfo.getDeviceGuid(), userGuid);
        Mono<String> tokenMono = cacheService.saveTokenForClient(tokenRequestBO, loginUserInfo.getLoginType(), loginUserInfo.getLoginSource());
        return tokenMono.map(Result::buildSuccessResult);
    }

}
