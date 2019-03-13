//package com.bridgelabz.fundoo.interceptor.interceptorhandler;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.bridgelabz.fundoo.util.UserToken;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class FundooInterceptorHandler implements HandlerInterceptor 
//{
//	@Autowired
//	UserToken userToken;
//	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
////		log.info("request api: "+request.getRequestURI());
////		String token = request.getHeader("token");
////		
////		long userID = userToken.tokenVerify(token);
////		request.setAttribute("token", userID);
//		//		return HandlerInterceptor.super.preHandle(request, response, handler);
//		return true;
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// TODO Auto-generated method stub
//		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// TODO Auto-generated method stub
//		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//	}
//
//}
