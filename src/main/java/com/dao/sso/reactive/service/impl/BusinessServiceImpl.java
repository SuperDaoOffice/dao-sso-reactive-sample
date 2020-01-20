package com.dao.sso.reactive.service.impl;

import com.dao.sso.reactive.entity.dto.LoginDTO;
import com.dao.sso.reactive.service.BusinessService;
import com.holderzone.framework.rocketmq.common.DefaultRocketMqProducer;
import com.holderzone.framework.util.JacksonUtils;
import com.holderzone.framework.util.StringUtils;
import com.holderzone.resource.common.dto.mq.UnMessage;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HuChiHui
 * @date 2020/01/20 上午 10:47
 * @description
 */
public class BusinessServiceImpl implements BusinessService {

    private static final Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);

    private static final String STORE_LOGIN_TIME_TOPIC = "enterprise-store-login-time-topic";
    private static final String STORE_LOGIN_TIME_TAG = "enterprise-store-login-time-tag";

    private DefaultRocketMqProducer producer;

    public BusinessServiceImpl(DefaultRocketMqProducer producer) {
        this.producer = producer;
    }

    @Override
    public void sendMessage(LoginDTO loginDTO) {
        //门店号为空表示非门店登录,不用发送登录消息
        if (StringUtils.isEmpty(loginDTO.getStoreNo())) {
            return;
        }
        UnMessage<Long> unMessage = new UnMessage<>();
        unMessage.setEnterpriseGuid(loginDTO.getMerchantNo());
        unMessage.setMessageType(loginDTO.getStoreNo());
        unMessage.setMessage(System.currentTimeMillis());
        unMessage.setStoreGuid(loginDTO.getStoreNo());
        try {
            producer.getProducer().sendOneway(new Message(STORE_LOGIN_TIME_TOPIC, STORE_LOGIN_TIME_TAG, JacksonUtils.toJsonByte(unMessage)));
        } catch (Exception e) {
            log.error("发送登录时间更新消息到mq失败", e);
        }
    }
}
