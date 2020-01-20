package com.dao.sso.reactive.handler.auth;

import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.dao.sso.reactive.service.CloudService;
import com.holderzone.framework.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2019/11/22 下午 16:08
 * @description
 */
public class CloudAuthHandler extends AbstractAuthHandler {

    private CloudService cloudSystemService;

    public CloudAuthHandler(CloudService cloudSystemService) {
        this.cloudSystemService = cloudSystemService;
    }

    @Override
    public Mono<AuthResultBO> auth(LoginUserInfoBO userInfo) {
        if (StringUtils.isEmpty(userInfo.getUsername())) {
            return Mono.just(AuthResultBO.buildUserNameEmptyResult());
        }
        if (StringUtils.isEmpty(userInfo.getPassword())) {
            return Mono.just(AuthResultBO.buildPasswordEmptyResult());
        }
        return cloudSystemService.loginUser(userInfo.getUsername(), userInfo.getPassword())
                .transform(validateUserDto());

    }
}
