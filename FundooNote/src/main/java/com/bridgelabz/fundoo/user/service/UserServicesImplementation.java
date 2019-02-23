package com.bridgelabz.fundoo.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.utility.UserToken;
import com.bridgelabz.fundoo.utility.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
@PropertySource("classpath:message.properties")
public class UserServicesImplementation implements IUserServices 
{

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private Util util;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Environment environment;
	
	@Override
	public Response addUser(UserDTO userDTO)
	{
		log.info(userDTO.toString());
		
		//getting user record by email
		Optional<User> avaiability = userRepository.findByEmail(userDTO.getEmail());

		if(avaiability.isPresent())
		{
			throw new UserException(environment.getProperty("9"));
		}

		//encrypting password by using BCrypt encoder
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user = modelMapper.map(userDTO, User.class);//storing value of one model into another

		 user.setAccount_registered(LocalDateTime.now());

		userRepository.save(user);

		util.send(user.getEmail(), "User Activation", util.getBody("192.168.0.134:8080/user/useractivation/",user.getUser_id()));
		
		Response statusInfo = util.statusInfo(environment.getProperty("1"), 200);
		
		System.out.println("res "+statusInfo.toString());
		return statusInfo;
	}
//
//	@Override
//	public String userLogin(LoginDTO loginDTO)
//	{
//		Optional<User> userEmail = userRepository.findByEmail(loginDTO.getEmail());
//
//		//String password = passwordEncoder.encode(LoginDTO.getPassword());
//		//	System.out.println("Password -> "+password);
//
//		//Optional<User> userPassword = userRepository.findBypassword(password);
//		String userPassword=userEmail.get().getPassword();
//
//		if(userEmail.get().isVarified()==true)
//		{
//			if(userEmail.isPresent()&&passwordEncoder.matches(loginDTO.getPassword(), userPassword))
//			{
//				String generatedToken = UserToken.generateToken(userEmail.get().getUser_id());
//
//				return generatedToken;
//			}
//			else
//			{
//				return "invalid";
//			}
//
//		}
//		return null;
//
//	}
//
//	//	@Override
//	//	public boolean isVerified() 
//	//	{
//	//		//logic
//	//		return true;
//	//	}
//
//	public boolean verifyToken(String token)
//	{
//		long userId = UserToken.tokenVerify(token);//taking decoded token id
//		System.out.println(userId);
//		Optional<User> checkVerify = userRepository.findById(userId).map(this::verify);
//		System.out.println(checkVerify);
//		System.out.println("Verification-> "+checkVerify.get().isVarified());
//
//		if(checkVerify.isPresent())
//			return true;
//		else
//			return false;
//	}
//
//	//setting true to activate the user in db
//	private User verify(User user) {
//		user.setVarified(true);
//
//		 user.setAccount_update(LocalDateTime.now());
//
//		return userRepository.save(user);
//	}
//
//	@Override
//	public void forgetPassword(String email)
//	{
//		Optional<User> user = userRepository.findByEmail(email);
//		long id = user.get().getUser_id();
//		//System.out.println(id);
//		//sending mail with reset link along with token
//		util.send(email, "PasswordReset", util.getBody("192.168.0.134:4200/resetPassword/",id));
//		//System.out.println("completed");
//	}
//
//	@Override
//	public boolean resetPassword(String token,String password)
//	{
//		System.out.println("Token ->"+token+"\n password"+password);
//
//		long userId = UserToken.tokenVerify(token);
//		String encodedPassword = passwordEncoder.encode(password);
//		Optional<User> user = userRepository.findById(userId);
//		user.get().setPassword(encodedPassword);
//		System.out.println(user.get());
//		//	User.setPassword(passwordEncoder.encode.getPassword()));
//		System.out.println("Encoded password"+encodedPassword);
//
//
//		//		System.out.println("dbuser ->"+dbUser);
//
//		userRepository.save(user.get());
//
//		System.out.println("Save Done");
//		return true;
//	}

}
