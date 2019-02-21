package com.bridgelabz.fundoo.note.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteException extends RuntimeException 
{

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMessage;
	
	
	
}
