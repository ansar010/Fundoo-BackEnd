package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.exception.UserException;

public interface IUserServices
{
	public boolean addUser(UserDTO userDTo) throws UserException, MessagingException, IllegalArgumentException, UnsupportedEncodingException;
	public String userLogin(LoginDTO loginDTO) throws IllegalArgumentException, UnsupportedEncodingException;
//	public void test(String string) throws MessagingException, UnsupportedEncodingException;
//	public boolean isVerified();
	public boolean verifyToken(String token) throws Exception;
	
	public void forgetPassword(String email) throws Exception;
	public boolean resetPassword(String token,String password) throws Exception;
		
}

