package com.dao.sso.reactive.util;

import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.EncodingException;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author HuChiHui
 * @date 2019/12/02 下午 14:53
 * @description
 */
public class CustomJwtEncoder implements Encoder<byte[], String> {

    public static final String UTF_8 = "utf-8";

    @Override
    public String encode(byte[] bytes) throws EncodingException {
        String str = new String(bytes);
        try {
            return Base64.getEncoder().encodeToString(str.getBytes(UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new EncodingException("编码不正确", e);
        }
    }
}
