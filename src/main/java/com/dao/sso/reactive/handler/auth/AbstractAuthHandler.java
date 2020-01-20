package com.dao.sso.reactive.handler.auth;

import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.holderzone.resource.common.dto.user.UserDTO;
import reactor.core.publisher.Mono;

import java.util.function.Function;


/**
 * @author HuChiHui
 * @date 2019/11/22 下午 15:58
 * @description
 */
public abstract class AbstractAuthHandler {

    /**
     * 认证
     *
     * @param userInfo
     * @return
     */
    abstract Mono<AuthResultBO> auth(LoginUserInfoBO userInfo);

    /**
     * 校验UserDTO
     *
     * @return
     */
    protected Function<Mono<UserDTO>, Mono<AuthResultBO>> validateUserDto() {
        return userMono ->
                userMono.map(userDTO -> {
                    if ("0".equals(userDTO.getIsEnabled())) {
                        return AuthResultBO.buildUserUnableResult();
                    }
                    return AuthResultBO.buildSuccessResult(userDTO);
                }).defaultIfEmpty(AuthResultBO.buildUserInfoErrorResult());
    }
}
