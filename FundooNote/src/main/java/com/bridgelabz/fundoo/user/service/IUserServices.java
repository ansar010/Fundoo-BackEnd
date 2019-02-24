package com.bridgelabz.fundoo.user.service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;

public interface IUserServices
{
	public Response addUser(UserDTO userDTo); 
	public Response Test(String name);
	public String userLogin(LoginDTO loginDTO); 
//	//	public void test(String string) throws MessagingException, UnsupportedEncodingException;
//	public boolean isVerified();
	public boolean verifyToken(String token);
//	
//	public void forgetPassword(String email) ;
//	public boolean resetPassword(String token,String password) ;
//		
}

