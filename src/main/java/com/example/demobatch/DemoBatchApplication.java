package com.example.demobatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoBatchApplication {
	public static Logger logger = LoggerFactory.getLogger(DemoBatchApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoBatchApplication.class, args);
	}
	
}
