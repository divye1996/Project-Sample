package com.cg.ibs.spmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cg")
public class SpringBootExampleApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootExampleApplication.class, args);
		System.out.println("Ready");
	}
}
