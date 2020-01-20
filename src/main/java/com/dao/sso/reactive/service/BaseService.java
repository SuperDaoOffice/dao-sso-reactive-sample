package com.dao.sso.reactive.service;

import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 10:15
 * @description
 */
public interface BaseService {

    /**
     * 验证验证码
     *
     * @param vid
     * @param vCode
     * @return
     */
    Mono<Boolean> verifyCodeValid(String vid, String vCode);
}
