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
import com.bridgelabz.fundoo.response.ResponseToken;
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

		User saveResponse = userRepository.save(user);
		
		if(saveResponse==null)
		{
			throw new UserException("Data not Saved..!, give propper input ");
		}
		
		System.out.println(user.getUser_id());
		util.send(user.getEmail(), "User Activation", util.getBody("192.168.0.134:8080/user/useractivation/",user.getUser_id()));
		
		Response statusInfo = util.statusInfo(environment.getProperty("1"), 200);
		
		System.out.println("res "+statusInfo.toString());
		return statusInfo;
	}

	@Override
	public ResponseToken userLogin(LoginDTO loginDTO)
	{
		log.info(loginDTO.toString());

		Optional<User> userEmail = userRepository.findByEmail(loginDTO.getEmail());
	
		
		if(!(userEmail.isPresent()))
		{
			throw new UserException(environment.getProperty("-9"));
		}
		
		String userPassword=userEmail.get().getPassword();

		if(userEmail.get().isVarified()==true)
		{
			if(userEmail.isPresent()&&passwordEncoder.matches(loginDTO.getPassword(), userPassword))
			{
				String generatedToken = UserToken.generateToken(userEmail.get().getUser_id());

				
				ResponseToken tokenStatusInfo = util.tokenStatusInfo(environment.getProperty("2"), 200, generatedToken);
				
				return tokenStatusInfo;
			}
			else
			{
				throw new UserException(environment.getProperty("-2"));

			}

		}
		else
		{
			throw new UserException(environment.getProperty("-12"));

		}
		

	}


	public boolean verifyToken(String token)
	{
		log.info("token->"+token);

		long userId = UserToken.tokenVerify(token);//taking decoded token id
		log.info("userId->"+userId);

		Optional<User> checkVerify = userRepository.findById(userId).map(this::verify);
		log.info("verification status ->"+checkVerify);

		if(checkVerify.isPresent())
			return true;
		else
			return false;
	}

	//setting true to activate the user in db
	private User verify(User user) 
	{
		log.info("userDetail->"+user);

		user.setVarified(true);

		 user.setAccount_update(LocalDateTime.now());

		return userRepository.save(user);
		
	}

	@Override
	public Response forgetPassword(String email)
	{
		log.info("email->"+email);

		Optional<User> user = userRepository.findByEmail(email);
		if(!(user.isPresent()))
		{
			throw new UserException(environment.getProperty("13"));
		}
		long id = user.get().getUser_id();

		//sending mail with reset link along with token
		util.send(email, "PasswordReset", util.getBody("192.168.0.134:4200/resetPassword/",id));

		Response statusInfo = util.statusInfo(environment.getProperty("5"), 200);
		
		return statusInfo;
	}

	@Override
	public Response resetPassword(String token,String password)
	{
		log.info("Token ->"+token+"\n password"+password);

		long userId = UserToken.tokenVerify(token);
		String encodedPassword = passwordEncoder.encode(password);
		Optional<User> user = userRepository.findById(userId);
		user.get().setPassword(encodedPassword);

		//	User.setPassword(passwordEncoder.encode.getPassword()));
		log.info("Encoded password"+encodedPassword);


		//		System.out.println("dbuser ->"+dbUser);

		userRepository.save(user.get());

		Response statusInfo = util.statusInfo(environment.getProperty("4"), 200);
		return statusInfo;
	}

	@Override
	public Response Test(String name) {
		
		Response statusInfo = util.statusInfo(environment.getProperty("1"), 200);
		return statusInfo;

	}

}
