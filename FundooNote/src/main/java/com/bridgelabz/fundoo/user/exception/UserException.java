package com.bridgelabz.fundoo.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	String errorMessage;
	int errorCode;
	
	public UserException(String errorMessage)
	{
		super(errorMessage);
	}
	public UserException(int errorCode,String errorMessage)
	{
		super(errorMessage);
	}
}
