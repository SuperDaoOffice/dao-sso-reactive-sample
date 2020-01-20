package com.dao.sso.reactive.entity;

/**
 * 登录来源类型
 *
 * @author fengchao
 * @date 2018/09/12 10:01
 */
public enum LoginSource {

    MERCHANT("0", "商户后台"),


    PCPAD("1", "PC版"),


    SHOPPASS("2", "小店通"),


    AIO("3", "一体机"),


    POS("4", "pos系列"),


    PAD("5", "pad"),


    M("6", "点菜宝"),


    V("7", "刷卡机"),


    WECHAT("8", "微信公众号"),


    KDS("9", "kds"),


    PHONE("10", "老板助手"),


    SELF("11", "自助点餐机"),


    MINAPP("13", "小程序"),


    TV("14", "TV"),


    CLOUD("99", "云端"),

    AGENT("90", "合作商"),

    ERP_WEB("100", "erp的web端"),

    ERP_NOT_WEB("101", "erp的非web端");

    private String code;
    private String msg;

    LoginSource(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }

    public String code() {
        return code;
    }

    public static LoginSource getSourceByCode(String code) {
        for (LoginSource loginSource : LoginSource.values()) {
            if (loginSource.code.equals(code)) {
                return loginSource;
            }
        }
        return null;
    }

    public static String getMsg(String code) {
        for (LoginSource loginSource : LoginSource.values()) {
            if (loginSource.code().equalsIgnoreCase(code)) {
                return loginSource.msg();
            }
        }
        return " ";
    }

    /**
     * 检查给定code是否为给定登录源集合中的一项
     *
     * @param sourceCode   来源code
     * @param loginSources 候选来源项集合
     * @return
     */
    public static Boolean checkLoginSourceInCollection(String sourceCode, LoginSource... loginSources) {
        LoginSource source = getSourceByCode(sourceCode);
        if (source != null && loginSources != null) {
            for (LoginSource loginSource : loginSources) {
                if (loginSource == loginSource) {
                    return true;
                }
            }
        }
        return false;
    }
}
