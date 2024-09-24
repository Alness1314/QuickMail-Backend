package com.alness.quickmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuickMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickMailApplication.class, args);
	}

}
