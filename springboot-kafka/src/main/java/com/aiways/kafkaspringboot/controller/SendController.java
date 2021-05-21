package com.aiways.kafkaspringboot.controller;

import com.aiways.kafkaspringboot.producer.Message;
import com.aiways.kafkaspringboot.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/kafka")
public class SendController {

    @Autowired
    private Producer producer;

    @GetMapping(value = "/send")
    public String send() {

        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg("msg from default topic");
        message.setSendTime(new Date());
        producer.sendMessage(message);
        producer.sendMessage("second", "msg from second topic");
        return "{\"code\":0}";
    }
}
