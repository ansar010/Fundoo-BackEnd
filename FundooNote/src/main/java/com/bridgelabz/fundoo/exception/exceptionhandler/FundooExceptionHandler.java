package com.bridgelabz.fundoo.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoo.exception.CollaboratorException;
import com.bridgelabz.fundoo.exception.EmailException;
import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.TokenException;
import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.util.StatusHelper;

@RestControllerAdvice
public class FundooExceptionHandler {

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Response> GlobalExceptionHandler(Exception e)
//	{
//		Response statusInfo = StatusHelper.statusInfo("Internal Error", -200);
//		return new ResponseEntity<>(statusInfo,HttpStatus.OK);	
//	}

	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> NoteExceptionHandler(NoteException e)
	{
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<>(response,HttpStatus.OK);	
	}
	
	@ExceptionHandler(CollaboratorException.class)
	public ResponseEntity<Response> CollaboratorExceptionHandler(CollaboratorException e)
	{
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<>(response,HttpStatus.OK);	
	}
	
	@ExceptionHandler(LabelException.class)
	public ResponseEntity<Response> LabelExceptionHandler(LabelException e)
	{
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<>(response,HttpStatus.OK);	
	}
	
	@ExceptionHandler(TokenException.class)
	public ResponseEntity<Response> tokenExceptionHandler(TokenException e)
	{
		Response statusInfo = StatusHelper.statusInfo(e.getMessage(),e.getErrorCode());
		return new ResponseEntity<>(statusInfo,HttpStatus.OK);	
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> UserExceptionHandler(UserException e)
	{
		Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
		return new ResponseEntity<>(response,HttpStatus.OK);	
	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<Response> EmailExceptionHandler(EmailException e)
	{
		Response statusInfo = StatusHelper.statusInfo(e.getMessage(),e.getErrorCode());
		return new ResponseEntity<Response>(statusInfo,HttpStatus.OK);	
	}

}
