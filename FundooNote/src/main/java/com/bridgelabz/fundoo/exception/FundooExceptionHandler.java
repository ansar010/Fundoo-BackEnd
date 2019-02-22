package com.bridgelabz.fundoo.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoo.response.Response;

@RestControllerAdvice
public class FundooExceptionHandler {

	@Autowired
	Response response;

	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> noteExceptionHandler(NoteException e)
	{
		response.setStatusMessage(e.getMessage());
		response.setStatusMessage(e.getMessage());
		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<Response> tokenExceptionHandler(TokenException e)
	{
		response.setStatusMessage(e.getMessage());
		response.setStatusCode(e.getErrorCode());

		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> UserExceptionHandler(NoteException e)
	{
		response.setStatusMessage(e.getMessage());
		response.setStatusMessage(e.getMessage());
		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}
	
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<Response> EmailExceptionHandler(NoteException e)
	{
		response.setStatusMessage(e.getMessage());
		response.setStatusMessage(e.getMessage());
		return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}

}
