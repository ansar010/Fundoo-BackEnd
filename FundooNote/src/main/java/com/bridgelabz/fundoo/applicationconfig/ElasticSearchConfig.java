//package com.bridgelabz.fundoo.applicationconfig;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.bridgelabz.fundoo.emailqueue.EmailQueueConsumer;
//
//@Configuration
//public class ElasticSearchConfig {
//
//	// name of Routing Key
//	public static final String ROUTING_KEY = "elastic";
//
//	// name of Queue
//	public static final String QUEUE_NAME = "elasticQueue";
//
//	// name of exchange 
//	public static final String EXCHANGE = "elasticExchange";
//
//	// Defining bean for rest high level client
//	@Bean(destroyMethod = "close")
//	public RestHighLevelClient client() {
//		
//		RestHighLevelClient client = new RestHighLevelClient(
//				RestClient.builder(new HttpHost("localhost", 9200, "http")));
//
//		return client;
//
//	}
//
//	
//	// Defining bean for queue
//	@Bean(name="elastic")
//	Queue elasticQueue() {
//		return new Queue(QUEUE_NAME, true);
//	}
//
//	// Defining bean for exchange 
//	@Bean
//	TopicExchange elasticExchange() {
//		return new TopicExchange(EXCHANGE);
//	}
//
//	//		// To convert message into JSON format 
//	//		@Bean
//	//		MessageConverter jsonMessageConverter() {
//	//			return new Jackson2JsonMessageConverter();
//	//		}
//
//	// To Bind message with routing key and send to exchange 
//	@Bean
//	Binding binding(@Qualifier("elastic") Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//	}
//
////	// Defining bean to Consume message 
////	@Bean
////	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
////			MessageListenerAdapter listenerAdapter) 
////	{
////		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
////		container.setConnectionFactory(connectionFactory);
////		container.setQueueNames(QUEUE_NAME);
////		container.setMessageListener(listenerAdapter);
////		return container;
////	}
////
////
////	@Bean
////	MessageListenerAdapter myQueueListener(EmailQueueConsumer  listener) {
////		return new MessageListenerAdapter(listener, "onMessage");
////	}
//}
