package com.bridgelabz.fundoo.user.utility;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Util 
{
	//	@Autowired
	//	JavaMailSender javaMailSender;


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

	public void send(String to, String subject, String body) throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		helper = new MimeMessageHelper(message, true); // true indicates
		// multipart message
		//helper.setFrom("NoReply-Fundoo");
		helper.setFrom(new InternetAddress("example@gmail.com", "NoReply-fundoo"));
		//helper.setReplyTo((InternetAddress.parse("fundoonote19@gmail.com",false)));
		helper.setReplyTo("fundoonote19@gmail.com");
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true); // true indicates html
		// continue using helper object for more functionalities like adding attachments, etc.  

		javaMailSender.send(message);
	}
	
	public String getBody(String link,long id) throws IllegalArgumentException, UnsupportedEncodingException
	{
		return link+UserToken.generateToken(id);
	}
}
