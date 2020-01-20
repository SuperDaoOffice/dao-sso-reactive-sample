package com.dao.sso.reactive.service;


import com.dao.sso.reactive.entity.TokenRequestBO;

import javax.crypto.SecretKey;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 11:03
 * @description
 */
public interface JwtService {

    /**
     * 生成token
     *
     * @param currentTimeMillis
     * @param tokenRequestBO
     * @return
     */
    String createJWT(long currentTimeMillis, TokenRequestBO tokenRequestBO);


    /**
     * 生成jwt的key
     *
     * @return
     */
    SecretKey generateKey();

}
