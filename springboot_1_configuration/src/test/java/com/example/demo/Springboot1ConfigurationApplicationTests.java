package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot1ConfigurationApplicationTests {

	@Test
	public void contextLoads() {
		String newpw = "123456";
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		String pw = bcryptPasswordEncoder.encode(newpw);
		boolean flag = bcryptPasswordEncoder.matches(newpw, pw);

		System.out.println("========================");
		System.out.println(pw);
		System.out.println(flag);
		System.out.println("========================");
	}



}
