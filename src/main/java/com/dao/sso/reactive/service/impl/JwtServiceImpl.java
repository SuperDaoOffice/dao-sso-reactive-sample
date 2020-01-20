package com.dao.sso.reactive.service.impl;

import com.dao.sso.reactive.entity.TokenRequestBO;
import com.dao.sso.reactive.service.JwtService;
import com.dao.sso.reactive.util.CustomJwtEncoder;
import com.dao.sso.reactive.util.JwtParser;
import com.holderzone.framework.exception.DecoderException;
import com.holderzone.framework.exception.unchecked.BusinessException;
import com.holderzone.framework.security.SecurityManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 11:03
 * @description
 */
public class JwtServiceImpl implements JwtService {

    /**
     * 服务端指定秘钥Key
     */
    private String javaJsonWebProfile;


    public JwtServiceImpl(String javaJsonWebProfile ){
        this.javaJsonWebProfile = javaJsonWebProfile;
    }

    /**
     * 秘钥key
     */
    public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";

    @Override
    public String createJWT(long currentTimeMillis, TokenRequestBO tokenRequestBO) {
        Claims claims = getCommonClaims(currentTimeMillis, tokenRequestBO);
        return createJWT(claims);
    }

    /**
     * 除erp之外的来源的payload的内容
     *
     * @param nowMillis
     * @param tokenRequestBO
     * @return
     */
    private Claims getCommonClaims(long nowMillis, TokenRequestBO tokenRequestBO) {
        Claims claims = Jwts.claims();
        claims.put(JwtParser.ENTERPRISE_GUID, tokenRequestBO.getEnterpriseGuid() == null ? "" : tokenRequestBO.getEnterpriseGuid());
        claims.put(JwtParser.STORE_NO, tokenRequestBO.getStoreNo() == null ? "" : tokenRequestBO.getStoreNo());
        claims.put(JwtParser.DEVICE_GUID, tokenRequestBO.getDeviceGuid() == null ? "" : tokenRequestBO.getDeviceGuid());
        claims.put(JwtParser.USER_GUID, tokenRequestBO.getUserGuid());
        claims.setIssuedAt(new Date(nowMillis));
        return claims;
    }

    /**
     * 创建jwt
     *
     * @param claims
     * @return
     */
    private String createJWT(Claims claims) {
        SecretKey key = generateKey();
        JwtBuilder builder = Jwts.builder()
                .base64UrlEncodeWith(new CustomJwtEncoder())
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuer("Holder.com")
                .signWith(key);
        return builder.compact();
    }

    @Override
    public SecretKey generateKey() {
        try {
            return Keys.hmacShaKeyFor(SecurityManager.decryptBase64ToByte(javaJsonWebProfile + JWT_SECRET));
        } catch (DecoderException e) {
            throw new BusinessException("生成密钥错误", e);
        }

    }
}
