package com.pt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.pt")
@SpringBootApplication
public class SpringBootRestPerformanceTestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestPerformanceTestsApplication.class, args);
	}

}
