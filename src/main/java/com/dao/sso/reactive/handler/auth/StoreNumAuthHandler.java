package com.dao.sso.reactive.handler.auth;

import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.dao.sso.reactive.service.EnterpriseService;
import com.dao.sso.reactive.service.UserService;
import com.holderzone.framework.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2019/11/22 下午 16:08
 * @description
 */
public class StoreNumAuthHandler extends AbstractAuthHandler {

    private UserService userService;

    private EnterpriseService enterpriseService;

    public StoreNumAuthHandler(UserService userService, EnterpriseService enterpriseService) {
        this.userService = userService;
        this.enterpriseService = enterpriseService;
    }


    @Override
    public Mono<AuthResultBO> auth(LoginUserInfoBO userInfo) {
        if (StringUtils.isEmpty(userInfo.getStoreNo())) {
            return Mono.just(AuthResultBO.buildStoreNumEmptyResult());
        }
        if (StringUtils.isEmpty(userInfo.getUsername())) {
            return Mono.just(AuthResultBO.buildUserNameEmptyResult());
        }
        if (StringUtils.isEmpty(userInfo.getPassword())) {
            return Mono.just(AuthResultBO.buildPasswordEmptyResult());
        }
        Mono<String> merchantMono = enterpriseService.findEnterpriseByStore(userInfo.getStoreNo());
        return merchantMono
                .flatMap(merchantNo -> {
                    if (StringUtils.isEmpty(merchantNo)) {
                        return Mono.just(AuthResultBO.buildAuthFailedResult("未找到相关的商户门店信息"));
                    }
                    return userService.loginUserByMerchantNo(merchantNo, userInfo.getUsername(), userInfo.getPassword())
                            .transform(validateUserDto());
                });
    }
}
