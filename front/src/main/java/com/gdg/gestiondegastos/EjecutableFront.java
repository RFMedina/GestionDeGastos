package com.gdg.gestiondegastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableFeignClients
public class EjecutableFront {

	public static void main(String[] args) {
		SpringApplication.run(EjecutableFront.class, args);
	}

}
