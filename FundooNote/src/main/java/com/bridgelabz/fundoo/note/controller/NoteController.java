package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.services.INoteService;
import com.bridgelabz.fundoo.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource("classpath:message.properties")
@RestController
@CrossOrigin(origins = "*" ,allowedHeaders = "*")

@RequestMapping("/user/note")
public class NoteController {

	@Autowired
	INoteService noteService;

	@Autowired
	Environment environment;
	
	@Autowired
	Response response;
		
	@PostMapping
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDTO,BindingResult bindingResult,@RequestHeader("token") String token)
	{
		log.info("Note-->"+noteDTO);
		log.info("token-->"+token);
		
		bindingResult(bindingResult);
		customValidation(noteDTO);
		
		Response response = noteService.createNote(noteDTO, token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Response> updateNote(@RequestBody Note note,BindingResult bindingResult,@RequestHeader("token") String token)
	{
		log.info("Note-->"+note);
		log.info("token-->"+token);
		
		bindingResult(bindingResult);
//		customValidation(note);
		
		Response response = noteService.updateNote(note, token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{noteId}")
	public ResponseEntity<Response> deleteNotePermenant(@PathVariable long noteId,@RequestHeader("token") String token)
	{
		log.info("Note-->"+noteId);
		log.info("token-->"+token);
		
		
		Response response = noteService.deleteForever(noteId, token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/trash/{noteId}")
	public ResponseEntity<Response> trashStatus(@PathVariable long noteId,@RequestHeader("token") String token)
	{
		log.info("Note-->"+noteId);
		log.info("token-->"+token);
		
		
		Response response = noteService.trashStatus(noteId, token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/pin/{noteId}")
	public ResponseEntity<Response> pinStatus(@PathVariable long noteId,@RequestHeader("token") String token)
	{
		log.info("Note-->"+noteId);
		log.info("token-->"+token);
		
		
		Response response = noteService.pinStatus(noteId, token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/archive/{noteId}")
	public ResponseEntity<Response> archiveStatus(@PathVariable long noteId,@RequestHeader("token") String token)
	{
		log.info("Note-->"+noteId);
		log.info("token-->"+token);
		
		
		Response response = noteService.archiveStatus(noteId, token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Note>> getAllNote(@RequestHeader("token") String token)
	{
		log.info("token-->"+token);
		
		
		List<Note> listOfNotes= noteService.getAllNote( token);
		
		return new ResponseEntity<>(listOfNotes,HttpStatus.CREATED);
	}
//	@PutMapping
//	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDTO,BindingResult bindingResult,
//												@RequestHeader("token") String token,@RequestParam long noteId)
//	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDTO,BindingResult bindingResult,
//			@RequestHeader("token") String token)
//
//	{
//		log.info("Note-->"+noteDTO);
//		log.info("token-->"+token);
//		
//		bindingResult(bindingResult);
//		customValidation(noteDTO);
//		
////		Response response = noteService.updateNote(noteDTO, token,noteId);
//		Response response = noteService.updateNote(noteDTO, token);
//
//		
//		return new ResponseEntity<>(response,HttpStatus.CREATED);
//	}
	
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
