package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SecurityapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityapiApplication.class, args);
	}

}
