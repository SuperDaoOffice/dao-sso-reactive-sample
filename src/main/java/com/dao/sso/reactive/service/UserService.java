package com.dao.sso.reactive.service;

import com.holderzone.resource.common.dto.user.UserDTO;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2020/01/18 上午 10:39
 * @description
 */
public interface UserService {

    /**
     * 根据手机号登录
     *
     * @param tel
     * @param password
     * @return
     */
    Mono<UserDTO> loginUserByTel(String tel, String password);

    /**
     * 根据商户号登录
     *
     * @param merchantNo
     * @param username
     * @param password
     * @return
     */
    Mono<UserDTO> loginUserByMerchantNo(String merchantNo, String username, String password);
}
