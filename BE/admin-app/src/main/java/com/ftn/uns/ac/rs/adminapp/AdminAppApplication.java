package com.ftn.uns.ac.rs.adminapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication()
public class AdminAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AdminAppApplication.class, args);
	}

}
