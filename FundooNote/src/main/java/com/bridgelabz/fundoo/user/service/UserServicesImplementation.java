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
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;
import com.bridgelabz.fundoo.util.MailHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
@PropertySource("classpath:message.properties")
public class UserServicesImplementation implements IUserServices 
{

	@Autowired
	private Environment environment;
	
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	MailHelper mailHelper;

	@Autowired
	private UserToken userToken;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ModelMapper modelMapper;



	@Override
	public Response addUser(UserDTO userDTO)
	{
		log.info(userDTO.toString());

		//getting user record by email
		Optional<User> avaiability = userRepository.findByEmail(userDTO.getEmail());

		if(avaiability.isPresent())
		{
			throw new UserException(environment.getProperty("status.register.emailExistError"),Integer.parseInt(environment.getProperty("status.register.errorCode")));
		}

		//encrypting password by using BCrypt encoder
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user = modelMapper.map(userDTO, User.class);//storing value of one model into another

		user.setAccount_registered(LocalDateTime.now());

		User saveResponse = userRepository.save(user);

		if(saveResponse==null)
		{
			throw new UserException(environment.getProperty("status.saveError"),Integer.parseInt(environment.getProperty("status.dataSaving.errorCode")));
		}

		System.out.println(user.getUserId());
		mailHelper.send(user.getEmail(), "User Activation", mailHelper.getBody("192.168.0.134:8080/user/useractivation/",user.getUserId()));
		
		Response response = StatusHelper.statusInfo(environment.getProperty("status.register.success"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	@Override
	public ResponseToken userLogin(LoginDTO loginDTO)
	{
		log.info(loginDTO.toString());

		Optional<User> userEmail = userRepository.findByEmail(loginDTO.getEmail());


		if(!(userEmail.isPresent()))
		{
			throw new UserException(environment.getProperty("status.login.unregAccError"),Integer.parseInt(environment.getProperty("status.login.errorCode")));
		}

		String userPassword=userEmail.get().getPassword();

		if(userEmail.get().isVarified()==true)
		{
			if(userEmail.isPresent()&&passwordEncoder.matches(loginDTO.getPassword(), userPassword))
			{
				String generatedToken = userToken.generateToken(userEmail.get().getUserId());

				ResponseToken responseToken = StatusHelper.tokenStatusInfo(environment.getProperty("status.login.success"),Integer.parseInt(environment.getProperty("status.success.code")),generatedToken);


				return responseToken;
			}
			else
			{
				throw new UserException(environment.getProperty("status.login.invalidInput"),Integer.parseInt(environment.getProperty("status.login.errorCode")));


			}

		}
		else
		{
			throw new UserException(environment.getProperty("status.login.invalidMail"),Integer.parseInt(environment.getProperty("status.login.errorCode")));

		}


	}

	public boolean verifyToken(String token)
	{
		log.info("token->"+token);

		long userId = userToken.tokenVerify(token);//taking decoded token id
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

		System.out.println("user "+user);
		
		return userRepository.save(user);

	}

	@Override
	public Response forgetPassword(String email)
	{
		log.info("email->"+email);

		Optional<User> user = userRepository.findByEmail(email);
		if(!(user.isPresent()))
		{
			throw new UserException(environment.getProperty("status.forgetPassword.invalidMail"),Integer.parseInt(environment.getProperty("status.forgetPassword.errorCode")));
		}
		long id = user.get().getUserId();

		//sending mail with reset link along with token
		mailHelper.send(email, "PasswordReset", mailHelper.getBody("192.168.0.134:4200/resetPassword/",id));

		Response response = StatusHelper.statusInfo(environment.getProperty("status.forgetPassword.success"),Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response resetPassword(String token,String password)
	{
		log.info("Token ->"+token+"\n password"+password);

		long userId = userToken.tokenVerify(token);
		String encodedPassword = passwordEncoder.encode(password);
		Optional<User> user = userRepository.findById(userId);
		user.get().setPassword(encodedPassword);

		//	User.setPassword(passwordEncoder.encode.getPassword()));
		log.info("Encoded password"+encodedPassword);


		//		System.out.println("dbuser ->"+dbUser);

		userRepository.save(user.get());

		Response response = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}
}
