package com.dao.sso.reactive.handler.entity;

import com.holderzone.framework.util.JacksonUtils;

import java.util.Map;

/**
 * @author HuChiHui
 * @date 2019/11/22 下午 15:53
 * @description
 */
public class AuthResultBO {

    /**
     * 认证是否成功
     */
    private Boolean success;

    /**
     * 认证失败原因
     */
    private String reason;

    /**
     * 用户信息Map
     */
    private Map<String, Object> userInfoMap;

    public AuthResultBO(Boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public AuthResultBO(Boolean success, Map<String, Object> userInfoMap) {
        this.success = success;
        this.userInfoMap = userInfoMap;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<String, Object> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, Object> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    public static AuthResultBO buildUserInfoErrorResult() {
        return new AuthResultBO(false, "用户名或密码错误");
    }

    public static AuthResultBO buildUserUnableResult() {
        return new AuthResultBO(false, "账号已被禁用");
    }

    public static AuthResultBO buildAuthFailedResult(String reason) {
        return new AuthResultBO(false, reason);
    }

    public static AuthResultBO buildUserNameEmptyResult() {
        return new AuthResultBO(false, "用户名为空");
    }

    public static AuthResultBO buildPasswordEmptyResult() {
        return new AuthResultBO(false, "密码为空");
    }

    public static AuthResultBO buildMerchantNumEmptyResult() {
        return new AuthResultBO(false, "商户号为空");
    }

    public static AuthResultBO buildPhoneNumEmptyResult() {
        return new AuthResultBO(false, "手机号为空");
    }

    public static AuthResultBO buildStoreNumEmptyResult() {
        return new AuthResultBO(false, "门店号为空");
    }

    public static AuthResultBO buildSuccessResult(Object obj) {
        Map<String, Object> userInfoMap = JacksonUtils.toMap(JacksonUtils.writeValueAsString(obj));
        return new AuthResultBO(true, userInfoMap);
    }

}
