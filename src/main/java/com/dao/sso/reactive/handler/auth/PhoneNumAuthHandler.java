package com.dao.sso.reactive.handler.auth;

import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.dao.sso.reactive.service.EnterpriseService;
import com.dao.sso.reactive.service.UserService;
import com.holderzone.framework.util.StringUtils;
import com.holderzone.resource.common.dto.user.UserDTO;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2019/11/22 下午 16:08
 * @description
 */
public class PhoneNumAuthHandler extends AbstractAuthHandler {

    private UserService userService;

    private EnterpriseService enterpriseService;

    public PhoneNumAuthHandler(UserService userService, EnterpriseService enterpriseService) {
        this.userService = userService;
        this.enterpriseService = enterpriseService;
    }

    @Override
    public Mono<AuthResultBO> auth(LoginUserInfoBO userInfo) {
        if (StringUtils.isEmpty(userInfo.getTel())) {
            return Mono.just(AuthResultBO.buildPhoneNumEmptyResult());
        }
        if (StringUtils.isEmpty(userInfo.getPassword())) {
            return Mono.just(AuthResultBO.buildPasswordEmptyResult());
        }
        Mono<UserDTO> userMono = userService.loginUserByTel(userInfo.getTel(), userInfo.getPassword());
        return userMono
                .transform(validateUserDto())
                .flatMap(authResultBO -> {
                    if (authResultBO.isSuccess()) {
                        Mono<Boolean> existMono = enterpriseService.validateEnterprise((String) authResultBO.getUserInfoMap().get("merchantNo"));
                        return existMono.map(exist -> exist ? authResultBO : AuthResultBO.buildAuthFailedResult("商户不可用"));
                    }
                    return Mono.just(authResultBO);
                });
    }
}
