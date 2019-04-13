package com.bridgelabz.fundoo.rabbitmq;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class EmailBody implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subject;
	private String to;
	private String body;
}
