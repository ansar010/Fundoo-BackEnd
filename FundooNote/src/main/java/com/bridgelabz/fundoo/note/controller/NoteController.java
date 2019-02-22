package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.services.INoteService;
import com.bridgelabz.fundoo.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource("classpath:message.properties")
@RestController
@RequestMapping("/user")
public class NoteController {

	@Autowired
	INoteService noteService;

	@Autowired
	Environment environment;
	
	@Autowired
	Response response;

	@PostMapping("/note")
	public ResponseEntity<Response> addNote(@RequestBody NoteDTO noteDTO,@RequestHeader("token") String token)
	{
		log.info("Note-->"+noteDTO);
		log.info("token-->"+token);
		
		noteService.addNote(noteDTO, token);
		
		response.setStatusCode(200);
		response.setStatusMessage(environment.getProperty("7"));
		
		return new ResponseEntity<Response>(response,HttpStatus.CREATED);
	}
}
