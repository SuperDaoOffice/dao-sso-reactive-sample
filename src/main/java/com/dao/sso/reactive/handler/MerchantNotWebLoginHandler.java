package com.dao.sso.reactive.handler;

import com.dao.sso.reactive.handler.auth.PhoneNumAuthHandler;
import com.dao.sso.reactive.handler.auth.StoreNumAuthHandler;
import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.dao.sso.reactive.service.BusinessService;
import com.dao.sso.reactive.service.CacheService;
import com.holderzone.framework.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 17:42
 * @description
 */
public class MerchantNotWebLoginHandler extends AbstractMerchantLoginHandler {

    private StoreNumAuthHandler storeNumAuthHandler;

    private PhoneNumAuthHandler phoneNumAuthHandler;

    public MerchantNotWebLoginHandler(StoreNumAuthHandler storeNumAuthHandler, PhoneNumAuthHandler phoneNumAuthHandler,
                                      CacheService cacheService, BusinessService businessService, Boolean verifyEnable) {
        super(cacheService, businessService, verifyEnable);
        this.storeNumAuthHandler = storeNumAuthHandler;
        this.phoneNumAuthHandler = phoneNumAuthHandler;
    }

    @Override
    public Mono<AuthResultBO> auth(LoginUserInfoBO userInfo) {
        if (!StringUtils.isEmpty(userInfo.getTel())) {
            return phoneNumAuthHandler.auth(userInfo);
        }
        return storeNumAuthHandler.auth(userInfo);
    }

    @Override
    public Mono<Boolean> isValidateVerifyCode(LoginUserInfoBO loginUserInfoBO) {
        return Mono.just(false);
    }


}
