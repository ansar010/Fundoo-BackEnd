package com.bridgelabz.fundoo.response;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
public class ResponseToken
{
	private String statusMessage;	
	private int statusCode;
	private String token;
	
}
