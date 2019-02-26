package com.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

//@Configuration
////@ComponentScan
//@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
//@SpringBootApplication
@ComponentScan(basePackages= {"com.bridgelabz.fundoo"})
//@SpringBootApplication(scanBasePackages = { "com.bridgelabz.fundoo" })
@SpringBootApplication
public class FundooApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooApplication.class, args);
	}

}

