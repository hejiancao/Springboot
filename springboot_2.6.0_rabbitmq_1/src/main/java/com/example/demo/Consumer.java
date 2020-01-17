package com.example.demo;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author created by shaos on 2019/8/15
 */
@Component
public class Consumer {

    private static Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //消息确认
            //basicAck(long deliveryTag, boolean multiple)
            //multiple=true: 消息id<=deliveryTag的消息，都会被确认
            //myltiple=false: 消息id=deliveryTag的消息，都会被确认
            int i = 1/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.info("消息被确认，消息为：[{}]", new String(message.getBody()));
        } catch (Exception e) {
            //消息拒绝
            //basicNack(long deliveryTag, boolean multiple, boolean requeue)
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.info("##消息被拒绝，并重新回到队列##");
        }
    }
}
