package com.bridgelabz.fundoo.user.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.dto.UserInfo;

public interface IUserServices
{
	public Response addUser(UserDTO userDTo,HttpServletRequest request); 
	public ResponseToken userLogin(LoginDTO loginDTO); 
	public boolean verifyToken(String token);
	public Response forgetPassword(String email) ;
	public Response resetPassword(String token,String password) ;
	public Response saveProfileImage(String token, MultipartFile file);
	public Resource getImage(String token);
	public UserInfo getUserInfo(String token);
	public Resource getCollabUserImage(long userId);
	
}

