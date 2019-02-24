package com.bridgelabz.fundoo.user.service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;

public interface IUserServices
{
	public Response addUser(UserDTO userDTo); 
	public Response Test(String name);
	public ResponseToken userLogin(LoginDTO loginDTO); 
//	//	public void test(String string) throws MessagingException, UnsupportedEncodingException;
//	public boolean isVerified();
	public boolean verifyToken(String token);
//	
	public Response forgetPassword(String email) ;
	public Response resetPassword(String token,String password) ;
//		
}

