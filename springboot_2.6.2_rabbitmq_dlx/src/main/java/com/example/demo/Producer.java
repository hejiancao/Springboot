package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.Constants.ORDER_EXCHANGE_NAME;
import static com.example.demo.Constants.ORDER_ROUTING_KEY;

/**
 * @author created by shaos on 2019/8/16
 */
@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置编码
            messageProperties.setContentEncoding("utf-8");
            // 设置过期时间5*1000毫秒
            messageProperties.setExpiration("5000");
            return message;
        };
        amqpTemplate.convertAndSend(ORDER_EXCHANGE_NAME, ORDER_ROUTING_KEY, "订单号：2020011913375374", messagePostProcessor);
    }

}
