package com.bridgelabz.fundoo.utility;

import org.springframework.beans.factory.annotation.Autowired;

public class Util {

//	UserToken userToken = new UserToken();

	@Autowired
	static userToken userToken;
	

	public static String getBody(String link,long id) 
	{
		return link+userToken.generateToken(id);
	}
	
}
