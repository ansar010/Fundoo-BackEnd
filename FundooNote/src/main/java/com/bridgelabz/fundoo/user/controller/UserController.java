package com.bridgelabz.fundoo.user.controller;

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
	private Environment environment;


	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO,BindingResult bindingResult)
	{
		logger.info("userDTO data"+userDTO.toString());
		logger.trace("User Registration");

		bindingResult(bindingResult);
		 
		Response statusResponse = userServices.addUser(userDTO);
		
		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);
	}
	
//	@PostMapping("/test")
//	public ResponseEntity<Response> test(@RequestParam String name )
//	{
//	Response test = userServices.Test(name);
//	return new ResponseEntity<Response>(test,HttpStatus.OK);
//	}


	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@RequestBody LoginDTO loginDTO,BindingResult bindingResult) 	{
		logger.info("Login Input "+loginDTO);
		logger.trace("User Login");
		
		bindingResult(bindingResult);

		ResponseToken userLoginResponse = userServices.userLogin(loginDTO);
		System.out.println(userLoginResponse);

		return new ResponseEntity<ResponseToken>(userLoginResponse, HttpStatus.OK);
	}

	
	@RequestMapping("/useractivation/{token}")
	public ResponseEntity<String> userVerification(@PathVariable String token)
	{
		logger.info("Token ->"+token);
		logger.trace("Email Verification");
		
		userServices.verifyToken(token);
		
		return new ResponseEntity<String>(environment.getProperty("status.activation.success"), HttpStatus.ACCEPTED);
	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody UserDTO userDto)
	{
		logger.info("Email->"+userDto.getEmail());

		Response statusInfo = userServices.forgetPassword(userDto.getEmail());
	
		return new ResponseEntity<Response>( statusInfo,HttpStatus.OK);
	}

	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@RequestBody String password, @PathVariable("token") String token)
	{
		logger.info("Password->"+password);
		logger.info("Token->"+token);
		
		Response resetPasswordResponse = userServices.resetPassword(token, password);
		
		return new ResponseEntity<Response>(resetPasswordResponse,HttpStatus.OK);
	}


	//	@RequestMapping("/testmail")
	//	public String sendMail() throws MessagingException, UnsupportedEncodingException 
	//	{
	//		//util.send("bandgar09@gmail.com","Test mail from Spring", "Hello ");
	//		//userServices.test("ansaruddeen030@gmail.com");
	//		return "success";
	//	}

	private void bindingResult(BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			logger.error("Error while binding user details");
			
			String statusMessge=environment.getProperty("status.bindingResult.errorCode");
			int statusCode=Integer.parseInt(environment.getProperty("status.bindingResult.invalidData"));
			
			throw new UserException(statusMessge,statusCode);
		}
	}
}
