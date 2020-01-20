package com.dao.sso.reactive.util;

import com.dao.sso.reactive.entity.TokenRequestBO;
import com.holderzone.framework.util.JacksonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author tiDong
 * @date 2017.10.25
 */
@Component
public class JwtParser {

    public static final String ENTERPRISE_GUID = "enterpriseGuid";

    public static final String ENTERPRISE_NAME = "enterpriseName";

    public static final String MERCHANT_NO = "merchantNo";

    public static final String STORE_NO = "storeNo";

    public static final String DEVICE_GUID = "deviceGuid";

    public static final String USER_GUID = "userGuid";

    public static final String USER_NAME = "userName";

    public static final String ACCOUNT = "account";

    public static final String TEL = "tel";

    @Value("${token.ttl}")
    public int JWT_REFRESH_TTL;

    /**
     * 解析签名(jwt的第三部分)
     *
     * @param token
     * @return
     */
    public String acquireSign(String token) {
        return token.split("\\.")[2];
    }

    public TokenRequestBO acquirePayload(String token) {
        byte[] decode = Base64.getDecoder().decode((token.split("\\.")[1]).getBytes(UTF_8));
        return JacksonUtils.toObject(TokenRequestBO.class, decode);

    }

    public String acquireUserFromToken(String token) {
        TokenRequestBO tokenRequestBO = acquirePayload(token);
        return tokenRequestBO.getUserGuid();
    }

    public String acquireEnterpriseFromToken(String token) {
        TokenRequestBO tokenRequestBO = acquirePayload(token);
        return tokenRequestBO.getEnterpriseGuid();
    }

    public String acquireStoreFromToken(String token) {
        TokenRequestBO tokenRequestBO = acquirePayload(token);
        return tokenRequestBO.getStoreNo();
    }

    public String acquireDeviceFromToken(String token) {
        TokenRequestBO tokenRequestBO = acquirePayload(token);
        return tokenRequestBO.getDeviceGuid();
    }

}
