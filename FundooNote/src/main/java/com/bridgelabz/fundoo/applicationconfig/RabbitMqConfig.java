package com.bridgelabz.fundoo.applicationconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

	// name of Queue
	public static final String ROUTING_KEY = "myQueue";

	// name of exchange 
	public static final String EXCHANGE = "MyExchange";

	// Defining bean for queue
	@Bean
	Queue queue() {
		return new Queue(ROUTING_KEY, true);
	}

	// Defining bean for exchange 
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}

	// To convert message into JSON format 
	@Bean
	MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// To Bind message with routing key and send to exchange 
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}
}
