package com.example.libraq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LibraqApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraqApplication.class, args);
	}
}
