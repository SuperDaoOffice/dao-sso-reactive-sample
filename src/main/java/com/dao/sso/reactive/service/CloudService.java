package com.dao.sso.reactive.service;

import com.holderzone.resource.common.dto.user.UserDTO;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 18:17
 * @description
 */
public interface CloudService {

    /**
     * 登录时查询用户信息
     *
     * @param username
     * @param password
     * @return
     */
    Mono<UserDTO> loginUser(String username, String password);

    /**
     * 禁用用户
     *
     * @param username
     * @return
     */
    Mono<Void> disableUserAtLogin(String username);
}
