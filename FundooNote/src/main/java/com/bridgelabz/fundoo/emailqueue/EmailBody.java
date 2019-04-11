package com.bridgelabz.fundoo.emailqueue;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class EmailBody {
	
	private String subject;
	private String to;
	private String body;
}
