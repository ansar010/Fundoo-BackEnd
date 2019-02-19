package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.exception.UserException;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.service.IUserServices;
import com.bridgelabz.fundoo.user.utility.Util;


@RestController
@CrossOrigin(origins = "*" ,allowedHeaders = "*",exposedHeaders= {"jwtToken"})
// .@CrossOrigin(origins= {"http://localhost:4200"},allowedHeaders = "*",exposedHeaders= {"jwtToken"})
@RequestMapping("/user")
@PropertySource("classpath:message.properties")
public class UserController 
{
	static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	IUserServices userServices;
	
	@Autowired
	Response response;
	
	@Autowired
	Util util;

	@Autowired
	private Environment environment;

	//	@PostMapping("/register")
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTo,BindingResult bindingResult) throws UserException, MessagingException, IllegalArgumentException, UnsupportedEncodingException
	{
		logger.info("userDTO data"+userDTo);
		logger.trace("User Registration");

		if(bindingResult.hasErrors())
		{
			//environment.g
			logger.error("Error while binding user details");
			throw new UserException("Data doesn't matched to field..!");
		}
		boolean check = userServices.addUser(userDTo);
		System.out.println("Environment "+environment.getProperty("a"));
		//response=new Response();
		if(check)
		{
			response.setStatusCode(200);
			response.setStatusMessge(environment.getProperty("a"));
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		else
		{
			response.setStatusMessge("fail to Register");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			//return "un success";
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO loginDTO,BindingResult bindingResult,HttpServletResponse httpServletResponse) throws UserException, IllegalArgumentException, UnsupportedEncodingException
	{
		logger.info("Login Input "+loginDTO);
		logger.trace("User Login");
		if(bindingResult.hasErrors())
		{
			logger.error("Error while binding user details");
			throw new UserException("Data doesn't matched to field..!");
		}
		String userToken = userServices.userLogin(loginDTO);
		System.out.println("token "+userToken);		
		//response = new Response();
		if(userToken!=null)
		{
			System.out.println("hi "+response);
			response.setStatusCode(200);
			response.setStatusMessge("Successfully login");

			httpServletResponse.addHeader("jwtToken", userToken);
//			System.out.println(resp);
			System.out.println(httpServletResponse.containsHeader("jwtToken"));
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		else
		{				
			response.setStatusMessge("Log in fail");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/userActivation/{token}")
	public ResponseEntity<String> userVerification(@PathVariable String token) throws Exception
	{
		System.out.println("hihihihihi");
		boolean check=userServices.verifyToken(token);

		if(check)
			return new ResponseEntity<String>("Activated", HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<String>("Not Activated", HttpStatus.NOT_ACCEPTABLE);

	}
	
	@PostMapping("/forgetpassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody UserDTO userDto) throws Exception
	{
		System.out.println("Email->"+userDto.getEmail());
		
		userServices.forgetPassword(userDto.getEmail());
		response.setStatusCode(200);
		response.setStatusMessge("password reset mail send to You");
		
		return new ResponseEntity<Response>( response,HttpStatus.OK);
	}
	
	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<String> resetPassword(@RequestBody UserDTO userdto, @PathVariable("token") String token) throws Exception
	{
		 userServices.resetPassword(token, userdto.getPassword());
		
		System.out.println("Token-> "+token);
		return new ResponseEntity<String>("Password Reset successfully ",HttpStatus.OK);
	}

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
