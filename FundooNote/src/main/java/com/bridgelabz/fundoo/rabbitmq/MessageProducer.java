package com.bridgelabz.fundoo.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.applicationconfig.RabbitMqConfig;
import com.bridgelabz.fundoo.note.model.Note;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageProducer {

	@Autowired
	AmqpTemplate amqpTemplate;
	
	public void sendMsgToEmailQueue(EmailBody data) {
		log.info("sending message to email Queue");
		log.info(data.toString());
		amqpTemplate.convertAndSend(RabbitMqConfig.EMAIL_EXCHANGE, RabbitMqConfig.EMAIL_ROUTING_KEY, data);
		log.info("The message has been sent to the email queue.");

	}
	
	public void sendMsgToElasticQueue(Note data) {
		log.info("sending message to elastic Queue");
		log.info(data.toString());
		amqpTemplate.convertAndSend(RabbitMqConfig.ELASTIC_EXCHANGE, RabbitMqConfig.ELASTIC_ROUTING_KEY, data);
		log.info("The message has been sent to the elastic queue.");

	}
}
