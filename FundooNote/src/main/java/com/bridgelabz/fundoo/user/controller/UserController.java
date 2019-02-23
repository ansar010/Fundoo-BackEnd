package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.service.IUserServices;
import com.bridgelabz.fundoo.utility.Util;


@RestController
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
// .@CrossOrigin(origins= {"http://localhost:4200"},allowedHeaders = "*",exposedHeaders= {"jwtToken"})

@RequestMapping("/user")

//annotation for set environment file 
@PropertySource("classpath:message.properties")
public class UserController 
{
	static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	IUserServices userServices;

	@Autowired
	ResponseToken response;
	
	@Autowired
	private Environment environment;

	@Autowired
	Util util;

//	@Autowired
//	private Environment environment;

	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO,BindingResult bindingResult)
	{
		logger.info("userDTO data"+userDTO.toString());
		logger.trace("User Registration");

		if(bindingResult.hasErrors())
		{
			logger.error("Error while binding user details");
			throw new UserException(environment.getProperty("10"));
		}
		 
		Response statusResponse = userServices.addUser(userDTO);
		System.out.println("Ansar  "+statusResponse.toString());
		
		 return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);

	}

//	@PostMapping("/login")
//	public ResponseEntity<ResponseToken> login(@RequestBody LoginDTO loginDTO,BindingResult bindingResult) throws UserException, IllegalArgumentException, UnsupportedEncodingException
//	{
//		logger.info("Login Input "+loginDTO);
//		logger.trace("User Login");
//		
//		if(bindingResult.hasErrors())
//		{
//			logger.error("Error while binding user details");
//			throw new UserException("Data doesn't matched to field..!");
//		}
//		String loginResponse = userServices.userLogin(loginDTO);
//		
//		//System.out.println("token "+userToken);		
//		//response = new Response();
//		System.out.println(loginResponse);
//		if(loginResponse.equals("invalid"))
//		{
//			response.setStatusCode(-200);
//			response.setStatusMessage(environment.getProperty("-2"));
//			return new ResponseEntity<ResponseToken>(response, HttpStatus.OK);
//		
//		} else {
//			//System.out.println("hi "+response);
//			response.setStatusCode(200);
//			response.setStatusMessage(environment.getProperty("2"));
//			response.setToken(loginResponse);
//			//httpServletResponse.addHeader("jwtToken", loginResponse);
//			//			System.out.println(resp);
//			//System.out.println(httpServletResponse.containsHeader("jwtToken"));
//			return new ResponseEntity<ResponseToken>(response, HttpStatus.OK);
//		}
//	}
//
//	@RequestMapping("/useractivation/{token}")
//	public ResponseEntity<String> userVerification(@PathVariable String token) throws Exception
//	{
//		logger.info("Token ->"+token);
//		logger.trace("Email Verification");
//		
//		userServices.verifyToken(token);
//
//		//response.setStatusMessage(environment.getProperty("3"));
//		
//		//return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
//		
//		return new ResponseEntity<String>(environment.getProperty("3"), HttpStatus.ACCEPTED);
//	}
//
//	@PostMapping("/forgetpassword")
//	public ResponseEntity<ResponseToken> forgetPassword(@RequestBody UserDTO userDto) throws Exception
//	{
//		//System.out.println("Email->"+userDto.getEmail());
//
//		userServices.forgetPassword(userDto.getEmail());
//		response.setStatusCode(200);
//		response.setStatusMessage(environment.getProperty("5"));
//
//		return new ResponseEntity<ResponseToken>( response,HttpStatus.OK);
//	}
//
//	@PutMapping("/resetpassword/{token}/{password}")
//	public ResponseEntity<ResponseToken> resetPassword(@PathVariable("password") String password, @PathVariable("token") String token) throws Exception
//	{
//		System.out.println(password);
//		userServices.resetPassword(token, password);
//		System.out.println(response);
//		//response = new Response();
//		response.setStatusCode(200);
//		response.setStatusMessage(environment.getProperty("4"));
//		//System.out.println("Token-> "+token);
//		return new ResponseEntity<ResponseToken>(response,HttpStatus.OK);
//	}

	//	@RequestMapping("/test")
	//	public ResponseEntity<String> userVer()
	//	{
	//		//boolean check=userServices.verifyToken(token);
	//
	//		//if(check)
	//			return new ResponseEntity<String>("Activated", HttpStatus.ACCEPTED);
	//		//else
	//			//return new ResponseEntity<String>("Not Activated", HttpStatus.NOT_ACCEPTABLE);
	//
	//	}

	//	@RequestMapping("/testmail")
	//	public String sendMail() throws MessagingException, UnsupportedEncodingException 
	//	{
	//		//util.send("bandgar09@gmail.com","Test mail from Spring", "Hello ");
	//		//userServices.test("ansaruddeen030@gmail.com");
	//		return "success";
	//	}

}
