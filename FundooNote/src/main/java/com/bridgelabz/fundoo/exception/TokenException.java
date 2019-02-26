package com.bridgelabz.fundoo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TokenException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	int errorCode;

	public TokenException(String msg,int errorCode) {
		super(msg);
		this.errorCode=errorCode;
	}
}
