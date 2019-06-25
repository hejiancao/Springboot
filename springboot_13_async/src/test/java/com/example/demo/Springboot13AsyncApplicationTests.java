package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot13AsyncApplicationTests {

	@Autowired
	private TestService testService;

	@Test
	public void contextLoads() {
		System.out.println("main--start");
		testService.async();
		System.out.println("main--end");
	}

}
