package com.bridgelabz.fundoo.applicationconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoo.rabbitmq.MessageConsumer;

@Configuration
//@PropertySource("classpath:application.properties")
public class RabbitMqConfig {

	// Queues 
	//	@Value("${email.queue.name}")
	public static final String EMAIL_QUEUE = "emailQueue";

	//	@Value("${elastic.queue.name}")
	public static final String ELASTIC_QUEUE = "elasticQueue";

	// Exchanges 
	//	@Value("${email.exchange.name}")
	public static final String EMAIL_EXCHANGE ="emailExchange";

	//	@Value("${elastic.exchange.name}")
	public static final String ELASTIC_EXCHANGE = "elasticExchange";

	// Routing-key 	
	//	@Value("${email.routing.key}")
	public static final String EMAIL_ROUTING_KEY ="email-key";

	//	@Value("${elastic.routing.key}")
	public static final String ELASTIC_ROUTING_KEY ="elastic-key";


	/* Creating a bean for the Email queue */
	@Bean
	//	@PostConstruct
	public Queue emailQueue() {
		return new Queue(EMAIL_QUEUE , false);
	}

	/* Creating a bean for the Email Exchange */
	@Bean
	//	@PostConstruct
	public TopicExchange emailExchange() {
		return new TopicExchange(EMAIL_EXCHANGE);
	}

	/* Binding between Exchange and Queue using routing key */
	@Bean
	public Binding emailQueueBidning() {
		return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(EMAIL_ROUTING_KEY);
	}


	/* Creating a bean for the Elastic queue */
	@Bean
	//	@PostConstruct
	public Queue elasticQueue() {
		return new Queue(ELASTIC_QUEUE , false);
	}

	/* Creating a bean for the elastic Exchange */
	@Bean
	//	@PostConstruct
	public TopicExchange elasticExchange() {
		return new TopicExchange(ELASTIC_EXCHANGE);
	}

	/* Binding between Exchange and Queue using routing key */
	@Bean
	public Binding elasticQueueBidning() {
		return BindingBuilder.bind(elasticQueue()).to(elasticExchange()).with(ELASTIC_ROUTING_KEY);
	}
	
	@Bean
	MessageListenerAdapter emailQueueListenerAdapter(MessageConsumer consumer) {
		return new MessageListenerAdapter(consumer, "emailQueueListener");
	}
	
//	 @Bean
//	    SimpleMessageListenerContainer persistenceListenerContainer(ConnectionFactory connectionFactory, @Qualifier("persistenceListenerAdapter") MessageListenerAdapter listenerAdapter) {
//	        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//	        container.setConnectionFactory(connectionFactory);
//	        container.setQueues(trashRouteQueue(), webAppQueue());
//	        container.setMessageListener(listenerAdapter);
//	        return container;
//	    }

	@Bean
	SimpleMessageListenerContainer emailQueueListener(ConnectionFactory connectionFactory,@Qualifier("emailQueueListenerAdapter") MessageListenerAdapter listenerAdapter) {
	
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(emailQueue());
		container.setMessageListener(listenerAdapter);

		return container;
	}
	
	 
	@Bean
	MessageListenerAdapter elasticQueueListenerAdapter(MessageConsumer consumer) {
		return new MessageListenerAdapter(consumer, "elasticQueueListener");
	}
	
	@Bean
	SimpleMessageListenerContainer elasticQueueListener(ConnectionFactory connectionFactory,@Qualifier("elasticQueueListenerAdapter")MessageListenerAdapter listenerAdapter) {
	
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(elasticQueue());
		container.setMessageListener(listenerAdapter);

		return container;
	}
	
}
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import com.bridgelabz.fundoo.emailqueue.EmailQueueConsumer;
//
//@Configuration
//public class RabbitMqConfig {
//
//	// name of Routing Key
//	public static final String ROUTING_KEY = "Ansar";
//	
//	// name of Queue
//	public static final String QUEUE_NAME = "emailQueue";
//
//	// name of exchange 
//	public static final String EXCHANGE = "mailExchange";
//
//	// Defining bean for queue
//	@Bean(name="mail")
////	@Primary
//	Queue queue() {
//		return new Queue(QUEUE_NAME, false);
//	}
//
//	// Defining bean for exchange 
//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange(EXCHANGE);
//	}
//
//	// To convert message into JSON format 
//	@Bean
//	MessageConverter jsonMessageConverter() {
//		return new Jackson2JsonMessageConverter();
//	}
//
//	// To Bind message with routing key and send to exchange 
//	@Bean
//	Binding binding(@Qualifier("mail") Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//	}
//	
//	// Defining bean to Consume message 
//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//											MessageListenerAdapter listenerAdapter) 
//	{
//	 SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//	 container.setConnectionFactory(connectionFactory);
//	 container.setQueueNames(QUEUE_NAME);
//	 container.setMessageListener(listenerAdapter);
//	 return container;
//	}
//
//
//	@Bean
//	MessageListenerAdapter myQueueListener(EmailQueueConsumer  listener) {
//	 return new MessageListenerAdapter(listener, "onMessage");
//	}
//}
