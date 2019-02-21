package com.bridgelabz.fundoo.note.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgelabz.fundoo.user.response.Response;

@RestControllerAdvice
public class NoteExceptionHandler {


	@ExceptionHandler(NoteException.class)
	public ResponseEntity<?> noteExceptionHandle(NoteException e)
	{
		Response response=new Response();
		response.setStatusCode(e.getErrorCode());
		response.setStatusMessage(e.getErrorMessage());
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> allExceptionHandle(Exception e)
	{
		Response response=new Response();
		response.setStatusCode(100);
		response.setStatusMessage(e.getMessage());
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}	
}
