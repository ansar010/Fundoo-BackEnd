//package com.bridgelabz.fundoo.emailqueue;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.bridgelabz.fundoo.util.MailHelper;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Service
//public class EmailQueueConsumer {
//
//	@Autowired
//	MailHelper mailHelper;
//	
//	public void onMessage(byte[] message)
//	{
//		System.out.println("Recieved Message"+message);
//		ObjectMapper objectMapper = new ObjectMapper();
//	
//	try {
//		EmailBody body = objectMapper.readValue(message, EmailBody.class);
//		
//		mailHelper.send(body.getTo(), body.getSubject(), body.getBody());
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	}
//	
//	
////	public void onMessage(String message) throws JsonParseException, JsonMappingException, IOException {
////		 try {
////		  Thread.sleep(5000);
////		 } catch (InterruptedException e) {
////		 
////		  e.printStackTrace();
////		 }
////
////		 System.out.println(message);
////		 String msg = new String(message);
////		 System.out.println("Message Received:"+msg);
////		 ObjectMapper objectMapper = new ObjectMapper();
////		 RabbitMqMessageBody body = objectMapper.readValue(msg, RabbitMqMessageBody.class);
////		 System.out.println("messsage body--->"+body);
////		 String emailid = body.getEmailId();
////		      String subject = body.getSubject();
////		      String url = body.getUrl();
////		      mailService.send(emailid, subject, url);
////		}
//}
