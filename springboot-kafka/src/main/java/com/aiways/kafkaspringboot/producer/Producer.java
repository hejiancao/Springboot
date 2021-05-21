package com.aiways.kafkaspringboot.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class Producer {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    // 发送消息
    public void sendMessage(Message message) {
        try {
            kafkaTemplate.send(kafkaTemplate.getDefaultTopic(), gson.toJson(message));
        } catch (Exception e) {
            log.error("发送数据出错！！！{}{}", kafkaTemplate.getDefaultTopic(), gson.toJson(message));
            log.error("发送数据出错=====>", e);
        }

        // 消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
                log.info("## Success -> topic={},partition={},key={},value={}", topic, partition, key, value);
            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
                log.error("## Error -> topic={},partition={},key={},value={}", topic, partition, key, value);
            }
        });
    }

    public void sendMessage(String topic, String data) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("kafka sendMessage success topic = {}, data = {}",topic, data);
            }
        });
    }
}
