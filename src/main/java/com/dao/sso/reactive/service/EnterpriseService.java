package com.dao.sso.reactive.service;

import com.holderzone.resource.common.dto.enterprise.EnterpriseDTO;
import com.holderzone.resource.common.dto.enterprise.EnterpriseQueryDTO;
import reactor.core.publisher.Mono;

/**
 * @author HuChiHui
 * @date 2020/01/18 上午 10:51
 * @description
 */
public interface EnterpriseService {

    /**
     * 查询企业信息
     *
     * @param queryDTO
     * @return
     */
    Mono<EnterpriseDTO> findEnterprise(EnterpriseQueryDTO queryDTO);

    /**
     * 校验企业
     *
     * @param merchantNo
     * @return
     */
    Mono<Boolean> validateEnterprise(String merchantNo);

    /**
     * 根据门店号查找merchantNo
     *
     * @param storeNo
     * @return
     */
    Mono<String> findEnterpriseByStore(String storeNo);
}
