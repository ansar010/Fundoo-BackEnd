package com.bridgelabz.fundoo.exception;

import lombok.Getter;

@Getter

public class EmailException extends RuntimeException
{

	private static final long serialVersionUID = 1L;
	int errorCode;

	public EmailException(String msg,int errorCode) {
		super(msg);
		this.errorCode=errorCode;
	}
}