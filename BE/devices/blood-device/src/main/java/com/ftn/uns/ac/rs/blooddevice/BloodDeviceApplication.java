package com.ftn.uns.ac.rs.blooddevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BloodDeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloodDeviceApplication.class, args);
	}

}
