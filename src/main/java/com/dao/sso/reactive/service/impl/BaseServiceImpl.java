package com.dao.sso.reactive.service.impl;

import com.dao.sso.reactive.service.BaseService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 10:16
 * @description
 */
public class BaseServiceImpl implements BaseService {

    private static final String VERIFY_CODE_URL = "http://base-service/verifycode?vc_id=${vcId}&vc_code=#{vcCode}";

    private WebClient.Builder webClientBuilder;

    public BaseServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Boolean> verifyCodeValid(String vid, String vCode) {
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("vcId", vid);
        paramMap.put("vcCode", vCode);
        return webClientBuilder.build()
                .post()
                .uri(VERIFY_CODE_URL, paramMap)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Boolean.class)
                .defaultIfEmpty(false);
    }
}
