package com.dao.sso.reactive.service.impl;

import com.dao.sso.reactive.service.CloudService;
import com.holderzone.resource.common.dto.user.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuChiHui
 * @date 2020/01/17 下午 18:18
 * @description
 */
@Service
public class CloudServiceImpl implements CloudService {

    private static final String CLOUD_LOGIN_USER_URL = "http://holder-saas-cloud-system/user/cloud/loginUser?userName={userName}&password={password}";

    private static final String DISABLE_USER_URL = "http://holder-saas-cloud-system/user/cloud/disableUserAtLogin?account={account}";

    private WebClient.Builder webClientBuilder;

    public CloudServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<UserDTO> loginUser(String username, String password) {
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("userName", username);
        paramMap.put("password", password);
        return webClientBuilder.build()
                .post()
                .uri(CLOUD_LOGIN_USER_URL, paramMap)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }

    @Override
    public Mono<Void> disableUserAtLogin(String username) {
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("account", username);
        return webClientBuilder.build()
                .post()
                .uri(DISABLE_USER_URL, paramMap)
                .exchange()
                .then();
    }
}
