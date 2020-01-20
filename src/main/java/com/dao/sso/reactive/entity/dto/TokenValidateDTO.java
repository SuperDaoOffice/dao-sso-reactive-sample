package com.dao.sso.reactive.entity.dto;

/**
 * @author HuChiHui
 * @date 2019/12/02 下午 16:12
 * @description
 */
public class TokenValidateDTO {

    private String token;

    private String source;

    private Integer loginType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }
}
