package com.dao.sso.reactive.controller;

import com.dao.sso.reactive.entity.LoginType;
import com.dao.sso.reactive.entity.dto.LoginDTO;
import com.dao.sso.reactive.handler.AbstractLoginHandler;
import com.dao.sso.reactive.handler.LoginHandlerFactory;
import com.dao.sso.reactive.handler.entity.AuthResultBO;
import com.dao.sso.reactive.handler.entity.LoginTransform;
import com.dao.sso.reactive.handler.entity.LoginUserInfoBO;
import com.holderzone.framework.exception.unchecked.BusinessException;
import com.holderzone.framework.exception.unchecked.ParameterException;
import com.holderzone.framework.util.JacksonUtils;
import com.holderzone.framework.util.StringUtils;
import com.holderzone.resource.common.util.LoginSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.holderzone.resource.common.util.LoginSource.AGENT;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 16:48
 * @description
 */
@RestController
@RequestMapping("/sso")
public class SsoController {

    private static final Logger log = LoggerFactory.getLogger(SsoController.class);

    @Autowired
    private LoginHandlerFactory loginHandlerFactory;

    private LoginTransform loginTransform = LoginTransform.INSTANCE;

    @PostMapping("/login")
    public Mono login(@RequestBody LoginDTO loginDTO, ServerWebExchange exchange) {
        log.info("登录接口入参： {}", JacksonUtils.writeValueAsString(loginDTO));
        LoginSource loginSource = getSource(exchange);
        LoginType loginType = getLoginType(exchange);
        if (AGENT.equals(loginSource)) {
            if (StringUtils.isEmpty(loginDTO.getOpenId())) {
                throw new ParameterException("openId不能为空");
            }
        }
        AbstractLoginHandler loginHandler = loginHandlerFactory.getLoginHandler(loginSource);
        LoginUserInfoBO loginUserInfoBO = loginTransform.loginDtoToLoginUserInfoBo(loginDTO);
        loginUserInfoBO.setLoginSource(loginSource);
        loginUserInfoBO.setLoginType(loginType);
        AuthResultBO[] authResultBOArray = new AuthResultBO[1];
        Mono<AuthResultBO> authResultMono = loginHandler.auth(loginUserInfoBO);
        return authResultMono
                .flatMap(authResultBO -> {
                    if (!authResultBO.isSuccess()) {
                        return loginHandler.handleAuthFailure(loginUserInfoBO, authResultBO.getReason())
                                .then(Mono.error(new BusinessException(authResultBO.getReason())));
                    }
                    authResultBOArray[0] = authResultBO;
                    return Mono.just(authResultBO);
                })
                .flatMap(val -> loginHandler.isValidateVerifyCode(loginUserInfoBO))
                .flatMap(validateVerifyCode -> {
                    if (validateVerifyCode) {
                        if (StringUtils.isEmpty(loginUserInfoBO.getVid()) || StringUtils.isEmpty(loginUserInfoBO.getvCode())) {
                            throw new ParameterException("验证码为空");
                        }
                        Mono<Boolean> validMono = loginHandler.verifyCodeValidate(loginUserInfoBO, authResultBOArray[0].getUserInfoMap());
                        return validMono.flatMap(valid -> {
                            if (!valid) {
                                throw new BusinessException("验证码错误");
                            }
                            return Mono.just(true);
                        });
                    }
                    return Mono.just(false);
                })
                .flatMap(val -> loginHandler.afterLogin(loginUserInfoBO, authResultBOArray[0].getUserInfoMap()));


    }

    /**
     * 获取登录来源
     *
     * @return
     */
    private LoginSource getSource(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> sourceList = headers.get("source");
        if (sourceList == null || sourceList.isEmpty()) {
            throw new BusinessException("没有登录来源");
        }
        String source = sourceList.get(0);
        LoginSource loginSource = LoginSource.getSourceByCode(source);
        if (loginSource == null) {
            throw new BusinessException("登录来源不允许");
        }
        return loginSource;
    }


    /**
     * 获取登录类型
     *
     * @return
     */
    private LoginType getLoginType(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> loginTypeList = headers.get("loginType");
        if (loginTypeList == null || loginTypeList.isEmpty()) {
            return LoginType.NOT_WEB;
        }
        String typeStr = loginTypeList.get(0);
        try {
            Integer type = Integer.parseInt(typeStr);
            LoginType loginType = LoginType.getTypeEnum(type);
            if (loginType == null) {
                throw new BusinessException("登录类型不允许");
            }
            return loginType;
        } catch (Exception e) {
            throw new BusinessException("登录类型不允许");
        }

    }
}
