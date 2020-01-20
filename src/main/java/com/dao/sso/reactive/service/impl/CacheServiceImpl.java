package com.dao.sso.reactive.service.impl;

import com.dao.sso.reactive.entity.LoginType;
import com.dao.sso.reactive.entity.TokenRequestBO;
import com.dao.sso.reactive.service.CacheService;
import com.dao.sso.reactive.service.JwtService;
import com.holderzone.resource.common.util.LoginSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author HuChiHui
 * @date 2020/01/18 下午 17:25
 * @description
 */
public class CacheServiceImpl implements CacheService {

    private static final Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);

    private static final String USER_LOGIN_COUNT = "USER_LOGIN_COUNT:";
    private static final String TOKEN_PREFIX = "token_";

    public int jwtRefreshTtl;

    public int jwtRefreshAppTtl;

    private ReactiveRedisTemplate redisTemplate;

    private JwtService jwtService;

    public CacheServiceImpl(JwtService jwtService, ReactiveRedisTemplate redisTemplate, Integer jwtRefreshTtl, Integer jwtRefreshAppTtl) {
        this.jwtService = jwtService;
        this.redisTemplate = redisTemplate;
        this.jwtRefreshTtl = jwtRefreshTtl;
        this.jwtRefreshAppTtl = jwtRefreshAppTtl;
    }

    @Override
    public Mono<Integer> incrementLoginCount(String username) {
        Mono<Long> loginCountMono = redisTemplate.opsForValue().increment(USER_LOGIN_COUNT + username, 1);
        Integer[] loginCountArray = new Integer[1];
        return loginCountMono.map(Long::intValue)
                .doOnNext(val -> loginCountArray[0] = val)
                .then(redisTemplate.expire(USER_LOGIN_COUNT + username, Duration.ofMinutes(5)))
                .thenReturn(loginCountArray[0]);
    }

    @Override
    public Mono<Integer> selectLoginCount(String username) {
        return redisTemplate.opsForValue().get(USER_LOGIN_COUNT + username)
                .defaultIfEmpty(0);
    }

    @Override
    public Mono<String> saveTokenForClient(TokenRequestBO tokenRequestBO, LoginType loginType, LoginSource source) {
        String token = jwtService.createJWT(System.currentTimeMillis(), tokenRequestBO);
        String cacheKey = TOKEN_PREFIX + tokenRequestBO.getUserGuid() + ":" + source.code();
        if (loginType == LoginType.WEB) {
            cacheKey = TOKEN_PREFIX + tokenRequestBO.getUserGuid();
        }
        long ttl = jwtRefreshTtl;
        if (source != LoginSource.MERCHANT && source != LoginSource.CLOUD) {
            ttl = jwtRefreshAppTtl;
        }
        return redisTemplate.opsForValue().set(cacheKey, token, Duration.ofMillis(ttl))
                .doOnNext(val -> log.info("保存token到redis,userGUID = {}", tokenRequestBO.getUserGuid()))
                .then(Mono.just(token));
    }

    @Override
    public Mono cleanLoginCount(String username) {
        return redisTemplate.delete(USER_LOGIN_COUNT + username);
    }
}
