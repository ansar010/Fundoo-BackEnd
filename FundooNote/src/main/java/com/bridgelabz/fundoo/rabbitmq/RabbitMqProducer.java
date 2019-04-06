package com.bridgelabz.fundoo.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.applicationconfig.RabbitMqConfig;

@Service
public class RabbitMqProducer {

	// template to send and receive message 
	@Autowired
	AmqpTemplate rabbitMqTemplate;
	
	public void sendMessageToQueue(RabbitMqMessageBody message)
	{
		System.out.println(message);
		rabbitMqTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,RabbitMqConfig.ROUTING_KEY,message);
		System.out.println("Im in a RabbitMq Producer");
	}
 }
