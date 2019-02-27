package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.services.INoteService;
import com.bridgelabz.fundoo.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource("classpath:message.properties")
@RestController
@RequestMapping("/user/note")
public class NoteController {

	@Autowired
	INoteService noteService;

	@Autowired
	Environment environment;
	
//	@Autowired
//	Response response;

	@PostMapping
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDTO,BindingResult bindingResult,@RequestHeader("token") String token)
	{
		log.info("Note-->"+noteDTO);
		log.info("token-->"+token);
		
		bindingResult(bindingResult);
		customValidation(noteDTO);
		
		Response response = noteService.addNote(noteDTO, token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
//	@PutMapping
//	public ResponseEntity<ResponseToken> updateNote(@RequestBody NoteDTO noteDTO,@RequestHeader("token") String token)
//	{
//		log.info("Note-->"+noteDTO);
//		log.info("token-->"+token);
//		
//		boolean d=noteService.updateNote(noteDTO,token);
//		response.setStatusCode(200);
//		response.setStatusMessage(environment.getProperty("8"));
//		
//		return new ResponseEntity<ResponseToken>(response,HttpStatus.CREATED);
//	}

	private void bindingResult(BindingResult bindingResult)
	{
		if(bindingResult.hasErrors())
		{
			log.error("Error while binding user details");

			String statusMessge=environment.getProperty("status.bindingResult.invalidData");
			int statusCode=Integer.parseInt(environment.getProperty("status.bindingResult.errorCode"));

			throw new NoteException(statusMessge,statusCode);
		}
	}
	

	private void customValidation(NoteDTO noteDTO)
	{
		if((noteDTO.getTitle().isEmpty())&&(noteDTO.getDescription().isEmpty()))
		{
			String statusMessge=environment.getProperty("status.note.validation");
			int statusCode=Integer.parseInt(environment.getProperty("status.noteValidation.errorCode"));

			throw new NoteException(statusMessge,statusCode);

		}
	}
}
