package com.bridgelabz.fundoo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

////@Configuration
////@ComponentScan
////@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@SpringBootApplication
@ComponentScan(basePackages= {"com.bridgelabz.fundoo"})
public class FundooApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooApplication.class, args);
	}

}

