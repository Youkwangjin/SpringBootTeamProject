package com.acorn.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WarehouseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseProjectApplication.class, args);
	}
}