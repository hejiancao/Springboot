package com.aiways.kafkaspringboot.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = {"defaultTopic","second"})
    public void processMessage(String content) {
        System.out.println("消息被消费" + content);
    }

}
