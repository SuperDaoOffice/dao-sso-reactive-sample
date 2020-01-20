package com.dao.sso.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude = RedisReactiveAutoConfiguration.class)
public class DaoSsoReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaoSsoReactiveApplication.class, args);
    }

}
