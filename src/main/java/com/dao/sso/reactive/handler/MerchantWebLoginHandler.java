package com.dao.sso.reactive.handler;

import com.dao.sso.reactive.handler.auth.MerchantNumAuthHandler;
import com.dao.sso.reactive.handler.auth.PhoneNumAuthHandler;
import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.dao.sso.reactive.service.BaseService;
import com.dao.sso.reactive.service.BusinessService;
import com.dao.sso.reactive.service.CacheService;
import com.holderzone.framework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 17:42
 * @description
 */
public class MerchantWebLoginHandler extends AbstractMerchantLoginHandler {

    private MerchantNumAuthHandler merchantNumAuthHandler;

    private PhoneNumAuthHandler phoneNumAuthHandler;

    private BaseService baseService;

    public MerchantWebLoginHandler(MerchantNumAuthHandler merchantNumAuthHandler, PhoneNumAuthHandler phoneNumAuthHandler,
                                   CacheService cacheService, BusinessService businessService,
                                   BaseService baseService, Boolean verifyEnable) {
        super(cacheService, businessService, verifyEnable);
        this.merchantNumAuthHandler = merchantNumAuthHandler;
        this.phoneNumAuthHandler = phoneNumAuthHandler;
        this.baseService = baseService;
    }

    @Override
    public Mono<AuthResultBO> auth(LoginUserInfoBO userInfo) {
        if (!StringUtils.isEmpty(userInfo.getTel())) {
            return phoneNumAuthHandler.auth(userInfo);
        }
        return merchantNumAuthHandler.auth(userInfo);
    }

    @Override
    public Mono<Boolean> isValidateVerifyCode(LoginUserInfoBO userInfoBO) {
        if (!StringUtils.isEmpty(userInfoBO.getTel())) {
            return Mono.just(false);
        }
        return super.isValidateVerifyCode(userInfoBO);
    }

    @Override
    public Mono<Boolean> verifyCodeValidate(LoginUserInfoBO loginUserInfoBO, Map<String, Object> userInfoMap) {
        return baseService.verifyCodeValid(loginUserInfoBO.getVid(), loginUserInfoBO.getvCode());
    }
}
