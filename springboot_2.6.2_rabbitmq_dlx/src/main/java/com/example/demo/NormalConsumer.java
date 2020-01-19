package com.example.demo;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**订单消费者
 * @author created by shaos on 2019/8/15
 */
@Component
public class NormalConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = Constants.ORDER_QUEUE_NAME)
    public void receive(Channel channel, Message message) throws IOException {
        try {
            int a = 1/0;
            logger.info("##订单队列接收到消息：[{}]##", new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.info("##消息被确认，消息为：[{}]##", new String(message.getBody()));
        } catch (Exception e) {
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//            logger.info("##消息被拒绝，并重新回到队列##");

            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            logger.info("##消息被拒绝，直接丢弃##");
        }
    }

}
