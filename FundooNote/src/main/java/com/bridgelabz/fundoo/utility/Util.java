package com.bridgelabz.fundoo.utility;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.exception.EmailException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;

@Component
public class Util 
{
	/**
	 * 
	 * @param to user mail id
	 * @param subject email message
	 * @param body consist the body of the message
	 * @return true 
	 * @throws MessagingException
	 */
	@Autowired
	private JavaMailSender javaMailSender;	
//	
	@Autowired
	Response response;
	
	public void send(String to, String subject, String body)
	{

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		
		try {
			// multipart message
			//helper.setFrom("NoReply-Fundoo");
			helper = new MimeMessageHelper(message, true); // true indicates

			helper.setFrom(new InternetAddress("example@gmail.com", "NoReply-fundoo"));
			
			helper.setReplyTo("fundoonote19@gmail.com");
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(body, true); // true indicates html
			// continue using helper object for more functionalities like adding attachments, etc.  

			javaMailSender.send(message);
		} catch (UnsupportedEncodingException | MessagingException e) {
			throw new EmailException("Exception while sending mail");
		}
		//helper.setReplyTo((InternetAddress.parse("fundoonote19@gmail.com",false)));
		
	}
	
	public String getBody(String link,long id) 
	{
		return link+UserToken.generateToken(id);
	}
	
	/**
	 * Method to return response object
	 * 
	 * @param statusMessage takes from user
	 * @param statusCode takes from user
	 * @return response object
	 */
	public Response statusInfo(String statusMessage,int statusCode)
	{
//		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setStatusMessage(statusMessage);
	
		return response;
	}
	
	/**
	 * Method to return responseToken object
	 * 
	 * @param statusMessage takes from user
	 * @param statusCode takes from user
	 * @return responseToken object
	 */
	public ResponseToken tokenStatusInfo(String statusMessage,int statusCode,String token)
	{
		ResponseToken tokenResponse = new ResponseToken();
		tokenResponse.setStatusCode(statusCode);
		tokenResponse.setStatusMessage(statusMessage);
		tokenResponse.setStatusMessage(token);

		return tokenResponse;
	}
}
