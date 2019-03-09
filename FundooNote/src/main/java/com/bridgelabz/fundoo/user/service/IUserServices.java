package com.bridgelabz.fundoo.user.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;

public interface IUserServices
{
	public Response addUser(UserDTO userDTo,HttpServletRequest request); 
	public ResponseToken userLogin(LoginDTO loginDTO); 
	public boolean verifyToken(String token);
	public Response forgetPassword(String email) ;
	public Response resetPassword(String token,String password) ;
}

