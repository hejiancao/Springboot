package com.example.demo;

import com.example.demo.pk1.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private BaseService baseService;

	@Test
	public void contextLoads() {
		Jedis jedis = baseService.getJedis();
		boolean lock = baseService.tryLock(jedis, "lock:123", "123", 6);
		System.out.println(lock);
	}

}
