package com.dao.sso.reactive.handler;

import com.holderzone.framework.exception.unchecked.BusinessException;
import com.holderzone.resource.common.util.LoginSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

import static com.dao.sso.reactive.config.HandlerConfig.*;
import static com.holderzone.resource.common.util.LoginSource.*;

/**
 * @author HuChiHui
 * @date 2019/11/23 下午 13:52
 * @description
 */
public class LoginHandlerFactory implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(LoginHandlerFactory.class);

    private Map<String, AbstractLoginHandler> loginHandlerMap = new HashMap<>(16);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.loginHandlerMap = applicationContext.getBeansOfType(AbstractLoginHandler.class);
    }

    /**
     * 根据登录来源获取登录处理器
     *
     * @param loginSource
     * @return
     */
    public AbstractLoginHandler getLoginHandler(LoginSource loginSource) {
        if (MERCHANT.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_WEB_LOGIN_HANDLER);
        } else if (AIO.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (POS.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (PAD.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (M.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (V.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (WECHAT.equals(loginSource)) {
            throw new BusinessException("不支持微信公众号登录");
        } else if (KDS.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (SELF.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (MINAPP.equals(loginSource)) {
            throw new BusinessException("不支持微信小程序登录");
        } else if (TV.equals(loginSource)) {
            return loginHandlerMap.get(MERCHANT_NOT_WEB_LOGIN_HANDLER);
        } else if (CLOUD.equals(loginSource)) {
            return loginHandlerMap.get(CLOUD_LOGIN_HANDLER);
        } else {
            throw new BusinessException("不支持的登录来源, source: " + loginSource);
        }
    }
}
