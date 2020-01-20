package com.dao.sso.reactive.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.TimeZone;

/**
 * @author HuChiHui
 * @date 2020/01/18 下午 16:35
 * @description
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public ReactiveRedisConnectionFactory lettuceConnectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(6))
                .shutdownTimeout(Duration.ZERO)
                .build();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setDatabase(database);
        configuration.setPassword(password);
        return new LettuceConnectionFactory(configuration, clientConfig);
    }

    @Bean
    public ReactiveRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory lettuceConnectionFactory) {
        return new ReactiveRedisTemplate(lettuceConnectionFactory,
                RedisSerializationContext.newSerializationContext()
                        .string(new StringRedisSerializer())
                        .value(getSerializer())
                        .hashValue(getSerializer())
                        .key(getSerializer())
                        .hashKey(getSerializer())
                        .build()
        );

    }

    /**
     * 序列化
     *
     * @return
     */
    private Jackson2JsonRedisSerializer getSerializer() {
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        // 设置时区
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略空bean转json的错误
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //转换localDatetime
        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        serializer.setObjectMapper(mapper);
        return serializer;
    }
}
