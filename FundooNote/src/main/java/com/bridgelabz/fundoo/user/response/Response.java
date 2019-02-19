package com.bridgelabz.fundoo.user.response;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Response class
@Getter
@Setter
@ToString
@Component
public class Response
{
	private String statusMessge;	
	private int statusCode;
	
}
