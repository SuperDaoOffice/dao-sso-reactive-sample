package com.dao.sso.reactive.service.impl;

import com.dao.sso.reactive.service.EnterpriseService;
import com.holderzone.resource.common.dto.enterprise.EnterpriseDTO;
import com.holderzone.resource.common.dto.enterprise.EnterpriseQueryDTO;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.dao.sso.reactive.util.CommonUtil.handleResponse;


/**
 * @author HuChiHui
 * @date 2020/01/18 上午 10:51
 * @description
 */
public class EnterpriseServiceImpl implements EnterpriseService {

    private static final String FIND_ENTERPRISE_URL = "http://holder-saas-cloud-enterprise/enterprise/find";

    private static final String FIND_ENTERPRISE_BY_STORE_URL = "http://holder-saas-cloud-enterprise/organization/store/code?storeCode={storeCode}";

    private WebClient.Builder webClientBuilder;

    public EnterpriseServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<EnterpriseDTO> findEnterprise(EnterpriseQueryDTO queryDTO) {
        return webClientBuilder.build()
                .post()
                .uri(FIND_ENTERPRISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(queryDTO), EnterpriseQueryDTO.class)
                .exchange()
                .transform(handleResponse(EnterpriseDTO.class));
    }

    @Override
    public Mono<Boolean> validateEnterprise(String merchantNo) {
        EnterpriseQueryDTO queryDTO = new EnterpriseQueryDTO();
        queryDTO.setUid(merchantNo);
        return findEnterprise(queryDTO)
                .map(enterpriseDTO -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<String> findEnterpriseByStore(String storeNo) {
        Map<String, String> paramMap = new HashMap<>(1);
        paramMap.put("storeCode", storeNo);
        return webClientBuilder.build()
                .get()
                .uri(FIND_ENTERPRISE_BY_STORE_URL, paramMap)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .transform(handleResponse(String.class));
    }
}
