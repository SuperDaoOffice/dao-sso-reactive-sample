package com.dao.sso.reactive.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author fengchao
 * @date 2018-06-27
 */
@ApiModel(description = "登录DTO")
public class LoginDTO {
    
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty("门店编号,一体机登录时传,web端不传")
    private String storeNo;

    @ApiModelProperty("商户号,商户后台登录时必传")
    private String merchantNo;

    @ApiModelProperty("设备GUID,门店终端登录时选传")
    private String deviceGuid;

    @ApiModelProperty(value = "微信公众号openId")
    private String openId;

    @ApiModelProperty("手机号,手机登录")
    private String tel;
    /**
     * 登录来源
     */
    @ApiModelProperty("登录来源:0/web,1/pcPad,2/小店通,3/aio,4/pos,5/pad,6/m1,7/p1 or v1,99/cloud")
    private String source;

    @ApiModelProperty("验证码id")
    private String vid;

    @ApiModelProperty("验证码code")
    private String vCode;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
