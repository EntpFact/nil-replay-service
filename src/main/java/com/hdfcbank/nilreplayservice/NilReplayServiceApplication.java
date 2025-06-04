package com.hdfcbank.nilreplayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hdfcbank")
public class NilReplayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NilReplayServiceApplication.class, args);
	}

}
