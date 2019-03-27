//package com.api.user.controller;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.List;
//import java.util.UUID;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import com.api.user.dto.CollabUserDetails;
//import com.api.user.dto.LoginDTO;
//import com.api.user.dto.UserDTO;
//import com.api.user.dto.UserInfo;
//import com.api.user.entity.User;
//import com.api.user.exception.UserException;
//import com.api.user.response.Response;
//import com.api.user.services.UserServices;
//import com.api.user.utils.EmailUtil;
//import com.api.user.utils.UserToken;
//
//@RestController
//@RequestMapping("/api/user")
//@CrossOrigin(origins = { "http://localhost:4200" }, exposedHeaders = { "jwtTokenxxx" })
//public class UserController {
//
//	static Logger logger = LoggerFactory.getLogger(UserController.class);
//
//	private final Path rootLocation = Paths.get("/home/administrator/Desktop/upload-files");
//
//	@Autowired
//	private UserServices userServices;
//
//	/**
//	 * @param user
//	 * @param bindingResult
//	 * @return response
//	 * @throws Exception
//	 */
//	@PostMapping("/register")
//	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult,
//			HttpServletRequest request) throws UserException {
//		logger.trace("User Registration");
//		Response response;
//		if (bindingResult.hasErrors()) {
//			/*
//			 * response = new FeildErrorResponse(bindingResult.getFieldErrors());
//			 * response.setStatusCode(-100); response.setStatusMessage("invalid Data");
//			 */
//			logger.error("Error in Binding The User Details");
//			throw new UserException(100, "Data Not Matching Eligiable Criteria");
//		}
//		User user = userServices.register(userDTO);
////		EmailUtil.sendEmail("prajapat.himanshu@gmail.com", "Successfully Registered", "Click On Below link To Verify your Email Address\n"+"http://localhost:8080/api/user/verifyemail?token="+registertoken);
//		EmailUtil.sendEmail(user.getEmail(), "Successful", getBody(request, user));
//
//		response = new Response();
//		response.setStatusCode(200);
//		response.setStatusMessage("User Register Sucessfully with id: " + user.getId() + "Please Verify Your EmailId");
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
//
//	/**
//	 * 
//	 * @param loginuser
//	 * @param bindingResult
//	 * @return ResponseEntity<Response>
//	 * @throws Exception
//	 */
//	@PostMapping("/login")
//	public ResponseEntity<Response> login(@RequestBody LoginDTO loginuser, BindingResult bindingResult,
//			HttpServletResponse h) throws UserException {
//		// System.out.println(loginuser);
//		logger.info("Login");
//		Response response;
//		String token;
//		try {
//			token = userServices.login(loginuser);
//		} catch (Exception e) {
//			throw new UserException(100, e.getMessage());
//		}
//
//		response = new Response();
//		response.setStatusCode(166);
//		response.setStatusMessage("Sucessfully logged in");
//
//		h.addHeader("jwtTokenxxx", token);
//		return new ResponseEntity<Response>(response, HttpStatus.OK);
//	}
//
//	/**
//	 * 
//	 * @param token
//	 * @param response
//	 * @throws Exception
//	 */
//	@GetMapping("/verifyemail/{token}")
//	public ResponseEntity<Response> verifyEmail(@PathVariable String token) throws UserException {
//		logger.info("User Verify");
//		Response response1;
//		try {
//			userServices.userVerify(token);
//			response1 = new Response();
//			response1.setStatusCode(200);
//			response1.setStatusMessage("Sucessfully logged in");
//			return new ResponseEntity<>(response1, HttpStatus.OK);
//		} catch (Exception exception) {
//			throw new UserException(100, "Invalid Verification Link");
//		}
//	}
//
//	/**
//	 * @Purpose It return Body of HttpRequest with token.
//	 * @param req
//	 * @param user
//	 * @return String
//	 * @throws Exception
//	 */
//	private String getBody(HttpServletRequest req, User user) throws UserException {
//
//		return ("http://localhost:4200/verify/" + UserToken.generateToken(user.getId()));
//	}
//
//	/**
//	 * 
//	 * @param token
//	 * @param email
//	 * @return
//	 * @throws UserException
//	 */
//	@GetMapping("/personid")
//	public ResponseEntity<Long> getUser(@RequestHeader("token") String token, @RequestParam String email)
//			throws UserException {
//		// System.out.println(token);
//		Long id = userServices.collabUserId(token, email);
//		return new ResponseEntity<Long>(id, HttpStatus.OK);
//	}
//
//	@PostMapping("/collabuserdetails")
//	public ResponseEntity<List<CollabUserDetails>> getDetails(@RequestBody List<Long> ids) throws UserException {
//		return new ResponseEntity<List<CollabUserDetails>>(userServices.userEmails(ids), HttpStatus.OK);
//	}
//
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
//
//	@GetMapping("/userid")
//	public ResponseEntity<Long> getUserId(@RequestParam String email) {
//		return new ResponseEntity<Long>(userServices.getUserId(email), HttpStatus.OK);
//	}
//	
//	@GetMapping("/getUserDetails")
//	public ResponseEntity<UserInfo> getUserInfo(@RequestHeader String token)throws UserException
//	{
//		return new ResponseEntity<UserInfo>(userServices.getUserInfo(token),HttpStatus.OK);
//		}
//}
