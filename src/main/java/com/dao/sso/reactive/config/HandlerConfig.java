package com.dao.sso.reactive.config;

import com.dao.sso.reactive.handler.CloudLoginHandler;
import com.dao.sso.reactive.handler.MerchantNotWebLoginHandler;
import com.dao.sso.reactive.handler.MerchantWebLoginHandler;
import com.dao.sso.reactive.handler.auth.CloudAuthHandler;
import com.dao.sso.reactive.handler.auth.MerchantNumAuthHandler;
import com.dao.sso.reactive.handler.auth.PhoneNumAuthHandler;
import com.dao.sso.reactive.handler.auth.StoreNumAuthHandler;
import com.dao.sso.reactive.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 11:40
 * @description
 */
@Configuration
public class HandlerConfig {

    public static final String CLOUD_LOGIN_HANDLER = "cloudLoginHandler";

    public static final String MERCHANT_WEB_LOGIN_HANDLER = "merchantWebLoginHandler";

    public static final String MERCHANT_NOT_WEB_LOGIN_HANDLER = "merchantNotWebLoginHandler";

    @Value("${verify.enable:true}")
    private Boolean verifyEnable;

    @Bean
    public CloudAuthHandler cloudAuthHandler(CloudService cloudService) {
        return new CloudAuthHandler(cloudService);
    }

    @Bean
    public MerchantNumAuthHandler merchantNumAuthHandler(EnterpriseService enterpriseService, UserService userService) {
        return new MerchantNumAuthHandler(enterpriseService, userService);
    }

    @Bean
    public PhoneNumAuthHandler phoneNumAuthHandler(EnterpriseService enterpriseService, UserService userService) {
        return new PhoneNumAuthHandler(userService, enterpriseService);
    }

    @Bean
    public StoreNumAuthHandler storeNumAuthHandler(EnterpriseService enterpriseService, UserService userService) {
        return new StoreNumAuthHandler(userService, enterpriseService);
    }

    @Bean(name = CLOUD_LOGIN_HANDLER)
    public CloudLoginHandler cloudLoginHandler(CloudAuthHandler cloudAuthHandler, CacheService cacheService,
                                               BaseService baseService, CloudService cloudService,
                                               BusinessService businessService) {
        return new CloudLoginHandler(cloudAuthHandler, cacheService, baseService, cloudService, businessService, verifyEnable);
    }

    @Bean(name = MERCHANT_NOT_WEB_LOGIN_HANDLER)
    public MerchantNotWebLoginHandler merchantNotWebLoginHandler(StoreNumAuthHandler storeNumAuthHandler, PhoneNumAuthHandler phoneNumAuthHandler,
                                                                 CacheService cacheService, BusinessService businessService) {
        return new MerchantNotWebLoginHandler(storeNumAuthHandler, phoneNumAuthHandler, cacheService, businessService, verifyEnable);
    }

    @Bean(name = MERCHANT_WEB_LOGIN_HANDLER)
    public MerchantWebLoginHandler merchantWebLoginHandler(MerchantNumAuthHandler merchantNumAuthHandler, PhoneNumAuthHandler phoneNumAuthHandler,
                                                           CacheService cacheService, BusinessService businessService, BaseService baseService) {
        return new MerchantWebLoginHandler(merchantNumAuthHandler, phoneNumAuthHandler, cacheService, businessService, baseService, verifyEnable);
    }
}
