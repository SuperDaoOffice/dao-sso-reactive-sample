package com.dao.sso.reactive.config;

import com.dao.sso.reactive.service.*;
import com.dao.sso.reactive.service.impl.*;
import com.holderzone.framework.rocketmq.common.DefaultRocketMqProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 11:42
 * @description
 */
@Configuration
public class ServiceConfig {

    @Value("${token.ttl}")
    public int jwtRefreshTtl;

    @Value("${token.app.ttl}")
    public int jwtRefreshAppTtl;

    @Value("${token.server.secret}")
    private String javaJsonWebProfile;

    @Bean
    public BaseService baseService(WebClient.Builder webClientBuilder) {
        return new BaseServiceImpl(webClientBuilder);
    }

    @Bean
    public BusinessService businessService(DefaultRocketMqProducer producer) {
        return new BusinessServiceImpl(producer);
    }

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImpl(javaJsonWebProfile);
    }

    @Bean
    public CacheService cacheService(JwtService jwtService, ReactiveRedisTemplate redisTemplate) {
        return new CacheServiceImpl(jwtService, redisTemplate, jwtRefreshTtl, jwtRefreshAppTtl);
    }

    @Bean
    public UserService userService(WebClient.Builder webClientBuilder) {
        return new UserServiceImpl(webClientBuilder);
    }

    @Bean
    public EnterpriseService enterpriseService(WebClient.Builder webClientBuilder) {
        return new EnterpriseServiceImpl(webClientBuilder);
    }
}
