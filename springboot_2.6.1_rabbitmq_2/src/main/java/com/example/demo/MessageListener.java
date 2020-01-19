package com.example.demo;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author created by shaos on 2020/1/17
 */
@Component
public class MessageListener implements ChannelAwareMessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {

//            int a = 1/0;

            /**1.通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
             换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它.
             2.提高服务稳定性。假设消费端有一段时间不可用，导致队列中有上万条未处理的消息，如果开启客户端， 巨量的消息推送过来，可能会导致
             消费端变卡，也有可能直接不可用，所以服务端限流很重要*/
            channel.basicQos(1);

            /**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
             当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。*/
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
