package com.dao.sso.reactive.service.impl;

import com.dao.sso.reactive.service.UserService;
import com.holderzone.resource.common.dto.user.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuChiHui
 * @date 2020/01/18 上午 10:39
 * @description
 */
public class UserServiceImpl implements UserService {

    private static final String LOGIN_BY_TEL_URL = "http://holder-saas-cloud-user/loginTel?tel={tel}&password={password}";

    private static final String LOGIN_BY_MERCHANT_NO_URL = "http://holder-saas-cloud-user/login?merchantNo={merchantNo}&account={account}&password={password}";

    private WebClient.Builder webClientBuilder;

    public UserServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<UserDTO> loginUserByTel(String tel, String password) {
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("tel", tel);
        paramMap.put("password", password);
        return webClientBuilder.build()
                .post()
                .uri(LOGIN_BY_TEL_URL, paramMap)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }

    @Override
    public Mono<UserDTO> loginUserByMerchantNo(String merchantNo, String username, String password) {
        Map<String, String> paramMap = new HashMap<>(3);
        paramMap.put("merchantNo", merchantNo);
        paramMap.put("account", username);
        paramMap.put("password", password);
        return webClientBuilder.build()
                .post()
                .uri(LOGIN_BY_MERCHANT_NO_URL,paramMap)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }
}
