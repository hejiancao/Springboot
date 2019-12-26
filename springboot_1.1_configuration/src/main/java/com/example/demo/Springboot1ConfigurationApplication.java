package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class Springboot1ConfigurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot1ConfigurationApplication.class, args);
	}

}
