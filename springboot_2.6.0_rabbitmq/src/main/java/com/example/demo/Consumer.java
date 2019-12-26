package com.example.demo;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author created by shaos on 2019/8/15
 */
@Component
public class Consumer {

    private static Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receive(Channel channel, Message message) throws IOException {
        try {
            /**
             * 自动确认
             * channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
             * 手动确认
             * channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
             * 否定确认 BasicNack 重新加入队列
             * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
             * 否定确认 BasicNack 丢弃
             * channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
             */

            logger.info("接收消息：{}", new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {

        }
    }

}
