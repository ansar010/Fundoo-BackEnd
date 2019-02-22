package com.bridgelabz.fundoo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private int errorCode;
	//private String errorMessage; 

	public TokenException(int errorCode,String errorMessage)
	{
		super(errorMessage);
		//this.errorMessage=errorMessage;
		this.errorCode=errorCode;
	}
}
