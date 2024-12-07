package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.security.service.IGestorProcesoPaso;

@SpringBootApplication
public class SecurityapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityapiApplication.class, args);
	}

}
