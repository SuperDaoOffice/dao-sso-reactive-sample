package com.dao.sso.reactive.handler.entity;


import com.dao.sso.reactive.entity.LoginType;
import com.holderzone.resource.common.util.LoginSource;

/**
 * @author HuChiHui
 * @date 2019/11/22 下午 15:50
 * @description
 */
public class LoginUserInfoBO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 门店编号
     */
    private String storeNo;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 设备GUID
     */
    private String deviceGuid;

    /**
     * 微信公众号openId
     */
    private String openId;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 登录来源
     */
    private LoginSource loginSource;

    /**
     * 登录类型
     */
    private LoginType loginType;

    /**
     * 验证码id
     */
    private String vid;

    /**
     * 验证码code
     */
    private String vCode;

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LoginSource getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(LoginSource loginSource) {
        this.loginSource = loginSource;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }
}
