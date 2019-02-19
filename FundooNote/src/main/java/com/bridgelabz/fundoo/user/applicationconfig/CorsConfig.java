package com.bridgelabz.fundoo.user.applicationconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration 
public class CorsConfig 
{
	@Autowired 
	private Environment environment;
	@Bean 
	public WebMvcConfigurer corsConfigurer()
	{ 
		return new WebMvcConfigurer() 
		{
			@Override public void addCorsMappings(CorsRegistry registry) 
			{
						registry.addMapping("/**") .allowedMethods(environment.getProperty("get"),
						environment.getProperty("post"), environment.getProperty("put"),
						environment.getProperty("delete")) .allowedOrigins("*").allowedHeaders("*");
			}
		};
	}
}