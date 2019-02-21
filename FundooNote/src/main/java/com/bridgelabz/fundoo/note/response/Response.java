package com.bridgelabz.fundoo.note.response;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
public class Response
{
	private String statusMessage;	
	private int statusCode;

}
