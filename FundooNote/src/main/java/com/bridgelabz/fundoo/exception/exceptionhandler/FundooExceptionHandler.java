package com.bridgelabz.fundoo.exception.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoo.exception.EmailException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.Util;

@RestControllerAdvice
public class FundooExceptionHandler {

	@Autowired
	Util util;
	
//	@Autowired
//	Response response;
//	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Response> GlobalExceptionHandler(NoteException e)
//	{
//		response.setStatusMessage(e.getMessage());
//		response.setStatusMessage(e.getMessage());
//		return new ResponseEntity<Response>(response,HttpStatus.OK);	
//	}
//
//	@ExceptionHandler(NoteException.class)
//	public ResponseEntity<Response> noteExceptionHandler(NoteException e)
//	{
//		Response statusInfo = util.statusInfo(e.getMessage(), -200);
//		return new ResponseEntity<Response>(statusInfo,HttpStatus.OK);	
//	}
//
//	@ExceptionHandler(TokenException.class)
//	public ResponseEntity<Response> tokenExceptionHandler(TokenException e)
//	{
//		response.setStatusMessage(e.getMessage());
//		response.setStatusCode(e.getErrorCode());
//
//		return new ResponseEntity<Response>(response,HttpStatus.OK);
//	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> UserExceptionHandler(UserException e)
	{
		Response statusInfo = util.statusInfo(e.getMessage(), -200);
		return new ResponseEntity<Response>(statusInfo,HttpStatus.OK);	
	}
	
//	@ExceptionHandler(EmailException.class)
//	public ResponseEntity<Response> EmailExceptionHandler(NoteException e)
//	{
//		response.setStatusMessage(e.getMessage());
//		response.setStatusMessage(e.getMessage());
//		return new ResponseEntity<Response>(response,HttpStatus.OK);	
//	}

}
