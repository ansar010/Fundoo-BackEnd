package com.bridgelabz.fundoo.utility;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.bridgelabz.fundoo.exception.EmailException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailHelper 
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
	private static JavaMailSender javaMailSender;	
	
	public static void sendEmail(String to, String subject, String body)
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
			log.error("Mail Exception"+e.getMessage());
			throw new EmailException("Exception while sending mail");
		}
		//helper.setReplyTo((InternetAddress.parse("fundoonote19@gmail.com",false)));
		
	}
	
	
	
	
}
