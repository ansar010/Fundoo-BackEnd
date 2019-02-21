package com.bridgelabz.fundoo.note.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.response.Response;

@Controller
public class NoteController {

	
	public ResponseEntity<Response> addNote(Note note,String token)
	{
		
		return null;
		
	}
}
