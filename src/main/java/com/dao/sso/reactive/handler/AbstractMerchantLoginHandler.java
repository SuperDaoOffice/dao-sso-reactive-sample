package com.dao.sso.reactive.handler;


import com.dao.sso.reactive.service.BusinessService;
import com.dao.sso.reactive.service.CacheService;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 17:42
 * @description
 */
public abstract class AbstractMerchantLoginHandler extends AbstractLoginHandler {

    public AbstractMerchantLoginHandler(CacheService cacheService , BusinessService businessService , Boolean verifyEnable) {
        super(cacheService , businessService,verifyEnable);
    }
}
