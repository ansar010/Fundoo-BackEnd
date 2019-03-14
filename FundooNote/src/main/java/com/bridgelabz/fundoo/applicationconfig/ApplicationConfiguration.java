//package com.bridgelabz.fundoo.applicationconfig;
//
//import org.apache.catalina.connector.Response;
//import org.modelmapper.ModelMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class ApplicationConfiguration 
//{
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}
//
//	//	@Bean
//	//	public static PropertySourcesPlaceholderConfigurer Property()
//	//	{
//	//		return new PropertySourcesPlaceholderConfigurer();
//	//	}
//
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
//
//	@Bean
//	public Response getResponse()
//	{
//		return new Response();
//	}
//	
//////	
////	@Bean
////	public UserToken getToken()
////	{
////		return new UserToken();
////	}
////	@Bean
////	public ResponseToken getResponseToken()
////	{
////		return new ResponseToken();
////	}
//}
