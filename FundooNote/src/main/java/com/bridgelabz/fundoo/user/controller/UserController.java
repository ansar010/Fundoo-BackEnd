package com.bridgelabz.fundoo.user.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

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

	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO,BindingResult bindingResult, HttpServletRequest request)
	{
		logger.info("userDTO data"+userDTO.toString());
		logger.trace("User Registration");

		bindingResult(bindingResult);

		Response statusResponse = userServices.addUser(userDTO,request);

		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@RequestBody LoginDTO loginDTO,BindingResult bindingResult) 	{
		logger.info("Login Input "+loginDTO);
		logger.trace("User Login");

		bindingResult(bindingResult);

		ResponseToken userLoginResponse = userServices.userLogin(loginDTO);
		System.out.println(userLoginResponse);

		return new ResponseEntity<ResponseToken>(userLoginResponse, HttpStatus.OK);
	}

//
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
	
	@GetMapping("/userdetails")
	public ResponseEntity<UserInfo> getuserInfo(@RequestHeader String token)
	{
		
		UserInfo userInfo = userServices.getUserInfo(token);
//		return resource;
		return new ResponseEntity<>(userInfo,HttpStatus.OK);
	}
//	@GetMapping("/getUserDetails")
//	public ResponseEntity<UserInfo> getUserInfo(@RequestHeader String token)throws UserException
//	{
//		return new ResponseEntity<UserInfo>(userServices.getUserInfo(token),HttpStatus.OK);
//		}
	
	
//	@PostMapping(value = "/imageupload")
//	public ResponseEntity<Response> profileImageSave(@RequestHeader("token") String token,
//			@RequestParam("file") MultipartFile file) throws UserException // @RequestHeader("token") String token,
//	{
//		UUID uuid = UUID.randomUUID();
//		String uuidString = uuid.toString();
//		try {
//			Files.copy(file.getInputStream(), this.rootLocation.resolve(uuidString),
//					StandardCopyOption.REPLACE_EXISTING);
//			userServices.setProfileImage(token, uuid.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Response response = new Response();
//		response.setStatusCode(166);
//		response.setStatusMessage("Image Uploaded");
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
//
//	@GetMapping("/imageget/{token}")
//	public Resource getProfilePic(@PathVariable String token) throws UserException {
//		long id = UserToken.tokenVerify(token);
//		System.out.println(id);
//		String fileName = userServices.getProfileImage(id);
//		Path file = rootLocation.resolve(fileName);
//		try {
//			Resource resource = new UrlResource(file.toUri());
//			if (resource.exists() || resource.isReadable()) {
//				return resource;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
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
