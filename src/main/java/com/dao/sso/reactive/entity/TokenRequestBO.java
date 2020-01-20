package com.dao.sso.reactive.entity;

/**
 * @author HuChiHui
 * @date 2019/11/26 下午 16:13
 * @description
 */
public class TokenRequestBO {

    private String enterpriseGuid;

    private String merchantNo;

    private String enterpriseName;

    private String storeNo;

    private String deviceGuid;

    private String userGuid;

    private String userName;

    private String account;

    private String tel;

    public TokenRequestBO() {
    }


    public TokenRequestBO(String enterpriseGuid, String storeNo, String deviceGuid, String userGuid) {
        this.enterpriseGuid = enterpriseGuid;
        this.storeNo = storeNo;
        this.deviceGuid = deviceGuid;
        this.userGuid = userGuid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnterpriseGuid() {
        return enterpriseGuid;
    }

    public void setEnterpriseGuid(String enterpriseGuid) {
        this.enterpriseGuid = enterpriseGuid;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }


}
