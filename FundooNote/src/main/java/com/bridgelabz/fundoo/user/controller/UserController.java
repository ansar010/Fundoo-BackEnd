/****************************************************************************************
 * purpose : UserController for get request and send response.
 *
 *@author Ansar
 *@version 1.8
 *@since 22/4/2019
 ****************************************************************************************/
package com.bridgelabz.fundoo.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.dto.UserInfo;
import com.bridgelabz.fundoo.user.service.IUserServices;


@RestController
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
// @CrossOrigin(origins= {"http://localhost:4202"},allowedHeaders = "*",exposedHeaders= {"jwtToken"})
@RequestMapping("/user")
//annotation for set environment file 
@PropertySource("classpath:message.properties")
public class UserController 
{
	static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserServices userServices;

	@Autowired
	private Environment environment;
	
	/**
	 * 
	 * @param userDTO takes user details
	 * @param bindingResult use to bind and validate data to respective model
	 * @param request use to take server address
	 * @return success response if user added to database
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO,BindingResult bindingResult, HttpServletRequest request)
	{
		logger.info("userDTO data",userDTO);

		logger.trace("User Registration");

		bindingResult(bindingResult);

		Response statusResponse = userServices.addUser(userDTO,request);

		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);
	}

	
	/**
	 * @param loginDTO takes login data 
	 * @param bindingResult bind the data to model
	 * @return response 
	 */
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
	
	@PostMapping("/imageupload")
	public ResponseEntity<Response> saveImage(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file)
	{
		
		Response response = userServices.saveProfileImage(token,file);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getimage/{token}")
	public ResponseEntity<Resource> getProfilePic(@PathVariable String token)
	{
		
		Resource resource = userServices.getImage(token);
//		return resource;
		return new ResponseEntity<>(resource,HttpStatus.OK);
	}
	
	@GetMapping("/getcollabuserimage/{userId}")
	public ResponseEntity<Resource> getCollabUserProfilePic(@PathVariable long userId)
	{
		
		Resource resource = userServices.getCollabUserImage(userId);
//		return resource;
		return new ResponseEntity<>(resource,HttpStatus.OK);
	}
	
	@GetMapping("/userdetails")
	public ResponseEntity<UserInfo> getuserInfo(@RequestHeader String token)
	{
		
		UserInfo userInfo = userServices.getUserInfo(token);
//		return resource;
		return new ResponseEntity<>(userInfo,HttpStatus.OK);
	}

	private void bindingResult(BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			logger.error("Error while binding user details");

			String statusMessge=environment.getProperty("status.bindingResult.invalidData");
			int statusCode=Integer.parseInt(environment.getProperty("status.bindingResult.errorCode"));

			throw new UserException(statusMessge,statusCode);
		}
	}
	
}
