package com.bridgelabz.fundoo.exception;

import lombok.Getter;

@Getter

public class CollaboratorException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	int errorCode;

	public CollaboratorException(String msg,int errorCode) {
		super(msg);
		this.errorCode=errorCode;
	}
}
