package com.bridgelabz.fundoo.rabbitmq;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class RabbitMqMessageBody {
	
	private String subject;
	private String to;
	private String body;
}
