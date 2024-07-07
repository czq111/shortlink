package com.nageoffer.shortlink.project.mq.producer;

import com.alibaba.fastjson2.JSON;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;


/**
 * 短链接监控状态保存消息队列生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ShortLinkStatusSaveProducer {
    private static final Logger log = LoggerFactory.getLogger(ShortLinkStatusSaveProducer.class);
    private final RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.topic}")
    private String statusSaveTopic;

    /**
     * 发送延迟消费统计
     */
    public void send(Map<String, String> produceMap) {
        String keys = UUID.randomUUID().toString();
        produceMap.put("keys", keys);
        Message<Map<String, String>> build = MessageBuilder
                .withPayload(produceMap)
                .setHeader(MessageConst.PROPERTY_KEYS, keys)
                .build();
        SendResult sendResult;
        try{
            sendResult=rocketMQTemplate.syncSend(statusSaveTopic, build, 2000L);
            log.info("[消息访问统计监控] 消息发送结果：{}，消息ID:{},消息keys：{}"
                    ,sendResult.getSendStatus(),sendResult.getMsgId(),keys);
        }catch (Throwable ex){
            log.error("[消息访问统计监控] 消息发送失败，消息体：{}", JSON.toJSONString(produceMap), ex);
        }

    }

}
