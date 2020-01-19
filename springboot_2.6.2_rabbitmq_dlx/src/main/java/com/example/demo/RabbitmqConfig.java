package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author created by shaos on 2019/10/24
 */
@Configuration
public class RabbitmqConfig {

    private static Logger logger = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean("queue")
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<String, Object>();
        // 当消息变成死信时路由到的交换机
        // 消息变成死信一般由于以下几种情况：
        //1. 消息被拒绝（Basic.Reject/Basic.Nack）且不重新投递（requeue=false）
        //2.消息过期
        //3.队列达到最大长度
        arguments.put("x-dead-letter-exchange", Constants.DLX_EXCHANGE_NAME);
        return new Queue(Constants.ORDER_QUEUE_NAME, true, false, false, arguments);
    }

    @Bean("exchange")
    public TopicExchange exchange() {
        return new TopicExchange(Constants.ORDER_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingExchange(@Qualifier("queue") Queue queue, @Qualifier("exchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constants.ORDER_ROUTING_KEY);
    }

    @Bean("dlxQueue")
    public Queue dlxQueue() {
        return new Queue(Constants.DLX_QUEUE_NAME);
    }
    @Bean("dlxExchange")
    public TopicExchange dlxExchange() {
        return new TopicExchange(Constants.DLX_EXCHANGE_NAME);
    }
    @Bean
    public Binding bindingDlxExchange(@Qualifier("dlxQueue") Queue dlxQueue, @Qualifier("dlxExchange") TopicExchange dlxExchange) {
        // routingKey必须是#
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("#");
    }


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

}
