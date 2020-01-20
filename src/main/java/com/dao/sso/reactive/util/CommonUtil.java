package com.dao.sso.reactive.util;

import com.holderzone.framework.exception.unchecked.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author HuChiHui
 * @date 2020/01/20 下午 17:15
 * @description
 */
public class CommonUtil {

    public static <T> Function<Mono<ClientResponse>, Mono<T>> handleResponse(final Class<T> clazz) {
        return mono -> mono
                .flatMap(response -> {
                    if (response.statusCode() == HttpStatus.OK) {
                        return response.bodyToMono(clazz);
                    } else {
                        return response.bodyToMono(String.class)
                                .flatMap(exception -> Mono.error(new BusinessException(exception)))
                                .then(response.bodyToMono(clazz));
                    }
                });
    }
}
