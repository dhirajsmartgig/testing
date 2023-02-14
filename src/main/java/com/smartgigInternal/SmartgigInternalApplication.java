package com.smartgigInternal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartgigInternalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartgigInternalApplication.class, args);
	}

}
