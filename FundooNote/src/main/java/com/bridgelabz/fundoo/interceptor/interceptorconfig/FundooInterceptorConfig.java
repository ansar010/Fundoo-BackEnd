//package com.bridgelabz.fundoo.interceptor.interceptorconfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
////import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import com.bridgelabz.fundoo.interceptor.interceptorhandler.FundooInterceptorHandler;
//
//public class FundooInterceptorConfig implements WebMvcConfigurer
//{
//	@Autowired
//	FundooInterceptorHandler interceptorHandler;
//		
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(interceptorHandler).addPathPatterns("/**");
//		
////		WebMvcConfigurer.super.addInterceptors(registry);
//	}
//
//}