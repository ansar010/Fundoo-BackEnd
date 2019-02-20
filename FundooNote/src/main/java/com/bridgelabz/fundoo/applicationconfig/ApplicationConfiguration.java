package com.bridgelabz.fundoo.applicationconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundoo.user.response.Response;

@Configuration
public class ApplicationConfiguration 
{
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	//	@Bean
	//	public static PropertySourcesPlaceholderConfigurer Property()
	//	{
	//		return new PropertySourcesPlaceholderConfigurer();
	//	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public Response getResponse()
	{
		return new Response();
	}
}
