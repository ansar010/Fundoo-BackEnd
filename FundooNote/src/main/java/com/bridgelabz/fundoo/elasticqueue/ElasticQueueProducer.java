//package com.bridgelabz.fundoo.elasticqueue;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bridgelabz.fundoo.applicationconfig.ElasticSearchConfig;
//import com.bridgelabz.fundoo.applicationconfig.RabbitMqConfig;
//import com.bridgelabz.fundoo.emailqueue.EmailBody;
//
//@Service
//public class ElasticQueueProducer {
//	
//		// template to send and receive message 
//		@Autowired
//		AmqpTemplate rabbitMqTemplate;
//		
//		public void sendMessageToElasticQueue(String data)
//		{
//			System.out.println(data.toString());
//			rabbitMqTemplate.convertAndSend(ElasticSearchConfig.EXCHANGE, ElasticSearchConfig.ROUTING_KEY, data);
////			rabbitMqTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,RabbitMqConfig.ROUTING_KEY,message);
//			System.out.println("Im in a RabbitMq Producer");
//		}
//}
