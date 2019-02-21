package com.bridgelabz.fundoo.note.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NoteController {

	
	public ResponseEntity<Response> addNote(Note note,String token)
	{
		log.info("Note-->"+note);
		log.info("token-->"+token);
		
		
		return null;
		
	}
}
