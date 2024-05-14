package com.example.demobatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DemoBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBatchApplication.class, args);
	}

}
