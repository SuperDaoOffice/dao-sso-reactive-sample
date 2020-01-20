package com.dao.sso.reactive.service;


import com.dao.sso.reactive.entity.dto.LoginDTO;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 10:46
 * @description
 */
public interface BusinessService {
    /**
     * 发送登录消息
     *
     * @param loginDTO
     */
    void sendMessage(LoginDTO loginDTO);
}
