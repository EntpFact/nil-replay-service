package com.hdfcbank.nilreplayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.hdfcbank")
@EnableScheduling
public class NilReplayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NilReplayServiceApplication.class, args);
	}

}
