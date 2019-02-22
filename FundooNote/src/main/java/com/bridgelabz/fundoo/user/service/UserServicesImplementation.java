package com.bridgelabz.fundoo.user.service;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.utility.UserToken;
import com.bridgelabz.fundoo.utility.Util;

@Service("userService")
public class UserServicesImplementation implements IUserServices 
{

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	Util util;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public boolean addUser(UserDTO userDTO)
	{
		//getting user record by email
		Optional<User> avaiability = userRepository.findByEmail(userDTO.getEmail());

		if(avaiability.isPresent())
		{
			//throw  new UserException("User Already Exist..!");
			return false;
		}

		//encrypting password by using BCrypt encoder
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User user = modelMapper.map(userDTO, User.class);//storing value of one model into another

		LocalDate date = LocalDate.now();
		user.setRegistered_date(date);

		userRepository.save(user);

		util.send(user.getEmail(), "User Activation", util.getBody("192.168.0.134:8080/user/useractivation/",user.getUser_id()));


		//generating token to activate user account
		//String token = UserToken.generateToken(user.getId());

		//sending mail to user along with generated token
		//util.send(userDTO.getEmail(),"User Activation", "link to Activate account: http://localhost:8080/userActivation/"+token);
		return true;
	}

	@Override
	public String userLogin(LoginDTO loginDTO)
	{
		Optional<User> userEmail = userRepository.findByEmail(loginDTO.getEmail());

		//String password = passwordEncoder.encode(LoginDTO.getPassword());
		//	System.out.println("Password -> "+password);

		//Optional<User> userPassword = userRepository.findBypassword(password);
		String userPassword=userEmail.get().getPassword();

		if(userEmail.get().isVarified()==true)
		{
			if(userEmail.isPresent()&&passwordEncoder.matches(loginDTO.getPassword(), userPassword))
			{
				String generatedToken = UserToken.generateToken(userEmail.get().getUser_id());

				return generatedToken;
			}
			else
			{
				return "invalid";
			}

		}
		return null;

	}

	//	@Override
	//	public boolean isVerified() 
	//	{
	//		//logic
	//		return true;
	//	}

	public boolean verifyToken(String token)
	{
		long userId = UserToken.tokenVerify(token);//taking decoded token id
		System.out.println(userId);
		Optional<User> checkVerify = userRepository.findById(userId).map(this::verify);
		System.out.println(checkVerify);
		System.out.println("Verification-> "+checkVerify.get().isVarified());

		if(checkVerify.isPresent())
			return true;
		else
			return false;
	}

	//setting true to activate the user in db
	private User verify(User user) {
		user.setVarified(true);

		LocalDate date = LocalDate.now();
		user.setAccount_update(date);

		return userRepository.save(user);
	}

	@Override
	public void forgetPassword(String email)
	{
		Optional<User> user = userRepository.findByEmail(email);
		long id = user.get().getUser_id();
		//System.out.println(id);
		//sending mail with reset link along with token
		util.send(email, "PasswordReset", util.getBody("192.168.0.134:4200/resetPassword/",id));
		//System.out.println("completed");
	}

	@Override
	public boolean resetPassword(String token,String password)
	{
		System.out.println("Token ->"+token+"\n password"+password);

		long userId = UserToken.tokenVerify(token);
		String encodedPassword = passwordEncoder.encode(password);
		Optional<User> user = userRepository.findById(userId);
		user.get().setPassword(encodedPassword);
		System.out.println(user.get());
		//	User.setPassword(passwordEncoder.encode.getPassword()));
		System.out.println("Encoded password"+encodedPassword);


		//		System.out.println("dbuser ->"+dbUser);

		userRepository.save(user.get());

		System.out.println("Save Done");
		return true;
	}

}
