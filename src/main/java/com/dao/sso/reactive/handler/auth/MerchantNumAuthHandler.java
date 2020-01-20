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
public class MerchantNumAuthHandler extends AbstractAuthHandler {

    private EnterpriseService enterpriseService;

    private UserService userService;

    public MerchantNumAuthHandler(EnterpriseService enterpriseService, UserService userService) {
        this.enterpriseService = enterpriseService;
        this.userService = userService;
    }

    @Override
    public Mono<AuthResultBO> auth(LoginUserInfoBO userInfo) {
        if (StringUtils.isEmpty(userInfo.getMerchantNo())) {
            return Mono.just(AuthResultBO.buildMerchantNumEmptyResult());
        }
        if (StringUtils.isEmpty(userInfo.getUsername())) {
            return Mono.just(AuthResultBO.buildUserNameEmptyResult());
        }
        if (StringUtils.isEmpty(userInfo.getPassword())) {
            return Mono.just(AuthResultBO.buildPasswordEmptyResult());
        }
        Mono<Boolean> existMono = enterpriseService.validateEnterprise(userInfo.getMerchantNo());
        return existMono
                .flatMap(exist -> {
                    if (exist) {
                        Mono<UserDTO> userMono = userService.loginUserByMerchantNo(userInfo.getMerchantNo(), userInfo.getUsername(), userInfo.getPassword());
                        return userMono.transform(validateUserDto());
                    }
                    return Mono.just(AuthResultBO.buildAuthFailedResult("商户已被删除"));
                });

    }
}
