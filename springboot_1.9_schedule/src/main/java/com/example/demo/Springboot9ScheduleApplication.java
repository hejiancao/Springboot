package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Springboot9ScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot9ScheduleApplication.class, args);
	}

}
