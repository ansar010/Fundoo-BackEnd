package com.bridgelabz.fundoo.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.applicationconfig.RabbitMqConfig;
import com.bridgelabz.fundoo.util.MailHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageConsumer {

	@Autowired
	MailHelper mailHelper;


	/**
	 * Message listener for email queue
	 * @param 
	 */
	@RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE)
	public void emailQueueListener(String data)
	{
		log.info("Received from email queue.", data);
	}

	public void elasticQueueListener(){

	}


	//    @RabbitListener(queues = "${app1.queue.name}")
	//    public void receiveMessageForApp1(final UserDetails data) {
	//    log.info("Received message: {} from app1 queue.", data);
	//    try {
	//    log.info("Making REST call to the API");
	//    //TODO: Code to make REST call
	//        log.info("<< Exiting receiveMessageForApp1() after API call.");
	//    } catch(HttpClientErrorException  ex) {
	//    if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
	//        log.info("Delay...");
	//        try {
	//    Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
	//    } catch (InterruptedException e) { }
	//    log.info("Throwing exception so that message will be requed in the queue.");
	//    // Note: Typically Application specific exception should be thrown below
	//    throw new RuntimeException();
	//    } else {
	//    throw new AmqpRejectAndDontRequeueException(ex); 
	//    }
	//    } catch(Exception e) {
	//    log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
	//    throw new AmqpRejectAndDontRequeueException(e); 
	//    }
	//    }
	
//	 /**
//     * Message listener for app2
//     * 
//     */
//    @RabbitListener(queues = "${app2.queue.name}")
//    public void receiveMessageForApp2(String reqObj) {
//    log.info("Received message: {} from app2 queue.", reqObj);
//    try {
//    log.info("Making REST call to the API");
//    //TODO: Code to make REST call
//        log.info("<< Exiting receiveMessageCrawlCI() after API call.");
//    } catch(HttpClientErrorException  ex) {
//    if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
//        log.info("Delay...");
//        try {
//    Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
//    } catch (InterruptedException e) { }
//    log.info("Throwing exception so that message will be requed in the queue.");
//    // Note: Typically Application specific exception can be thrown below
//    throw new RuntimeException();
//    } else {
//    throw new AmqpRejectAndDontRequeueException(ex); 
//    }
//    } catch(Exception e) {
//    log.error("Internal server error occurred in python server. Bypassing message requeue {}", e);
//    throw new AmqpRejectAndDontRequeueException(e); 
//    }
//    }

}
