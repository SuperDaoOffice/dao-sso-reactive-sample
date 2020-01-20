package com.dao.sso.reactive.entity;

/**
 * @author HuChiHui
 * @date 2019/11/26 下午 15:16
 * @description
 */
public enum LoginType {

    WEB(0),

    NOT_WEB(1);

    private Integer type;

    LoginType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static LoginType getTypeEnum(Integer type) {
        for (LoginType loginType : LoginType.values()) {
            if (loginType.getType().equals(type)) {
                return loginType;
            }
        }
        return null;
    }
}
