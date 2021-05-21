package com.aiways.kafkaspringboot;

import com.aiways.kafkaspringboot.producer.Message;
import com.aiways.kafkaspringboot.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
class KafkaSpringbootApplicationTests {

	@Autowired
	Producer producer;

	@Test
	void contextLoads() {
	}


}
