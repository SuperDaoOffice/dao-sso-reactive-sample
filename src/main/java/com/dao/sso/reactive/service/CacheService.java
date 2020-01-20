package com.dao.sso.reactive.service;

import com.dao.sso.reactive.entity.LoginType;
import com.dao.sso.reactive.entity.TokenRequestBO;
import com.holderzone.resource.common.util.LoginSource;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2020/01/18 下午 17:25
 * @description
 */
public interface CacheService {

    /**
     * 增大登录次数
     *
     * @param username
     * @return
     */
    Mono<Integer> incrementLoginCount(String username);

    /**
     * 查询登录次数
     *
     * @param username
     * @return
     */
    Mono<Integer> selectLoginCount(String username);

    /**
     * 保存token
     *
     * @param tokenRequestBO
     * @param loginType
     * @param loginSource
     * @return
     */
    Mono<String> saveTokenForClient(TokenRequestBO tokenRequestBO, LoginType loginType, LoginSource loginSource);

    /**
     * 清除登录次数
     *
     * @param username
     * @return
     */
    Mono<Void> cleanLoginCount(String username);
}
