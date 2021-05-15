package com.ftn.uns.ac.rs.adminapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@EnableAsync
@SpringBootApplication()
public class AdminAppApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(AdminAppApplication.class, args);
	}

}
