package com.poly.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.poly.demo")
public class ChickenFastServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChickenFastServiceApplication.class, args);
	}

}
