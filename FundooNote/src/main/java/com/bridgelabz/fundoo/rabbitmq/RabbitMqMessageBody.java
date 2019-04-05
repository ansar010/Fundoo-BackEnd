package com.bridgelabz.fundoo.rabbitmq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RabbitMqMessageBody {

	private String from;
	private String to;
	private String message;
}
