package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author created by shaos on 2019/8/15
 */
@Configuration
public class RabbitMQConfig {

    private static Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
    public static final String QUEUE = "direct_queue";
    public static final String EXCHANGE = "direct_exchange";
    public static final String ROUTING_KEY = "info";

    @Autowired
    private CachingConnectionFactory connectionFactory;


    @Bean("queue")
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean("exchange")
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingExchange(@Qualifier("queue") Queue queue,
                                   @Qualifier("exchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }


    /** 发布者设置：发送确认，失败通知
     * @author shaos
     * @date 2020/1/17 18:10
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        /**
         * 若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback，如果这里配置了，那么写生产者的时候不能再写confirm-callback和return-callback
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true
         */
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);


        /**
         * 如果消息没有到exchange,则confirm回调,ack=false
         * 如果消息到达exchange,则confirm回调,ack=true
         * exchange到queue成功,则不回调return
         * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    logger.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
                } else {
                    logger.info("消息发送失败:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.error("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
            }
        });
        return rabbitTemplate;
    }


    /** 消费者设置：并发消费者数量，消息确认，qos限流
     * @author shaos
     * @date 2020/1/17 18:07
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(MessageListener messageListener) {
        // 加载处理消息A的队列
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 设置接收多个队列里面的消息，这里设置接收队列A
        // 假如想一个消费者处理多个队列里面的信息可以如下设置：
        // container.setQueues(queueA(),queueB(),queueC());
        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        // 设置最大的并发的消费者数量
        container.setMaxConcurrentConsumers(15);
        // 最小的并发消费者的数量
        container.setConcurrentConsumers(15);
        // 设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(messageListener);
        return container;
    }


}
