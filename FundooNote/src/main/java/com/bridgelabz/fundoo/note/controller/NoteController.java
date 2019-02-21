package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.INoteService;
import com.bridgelabz.fundoo.user.response.Response;

@RestController
////@RequestMapping("/fundoo")

@CrossOrigin(origins= {"http://localhost:4203"},allowedHeaders = "*",
exposedHeaders= {"token"})
public class NoteController 
{

	Response response;
	@Autowired
	INoteService noteService;


	
	@RequestMapping(value="/note",method=RequestMethod.POST) 
	public ResponseEntity<Response> addNoteToUser(@RequestBody Note note,@RequestHeader("token")String token)
	{

		noteService.addNote(token,note);
		
		response=new Response();

		response.setStatusCode(166);
		response.setStatusMessage("note added successfully");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/note",method=RequestMethod.PUT)
	public ResponseEntity<Response> editNote(@RequestBody Note note,@RequestHeader("token") String token)
	{
		boolean check=noteService.updateNote(note,token);
		response=new Response();
		if(check)
		{
			response.setStatusCode(166);
			response.setStatusMessage("note updated successfully");
			return new ResponseEntity<Response>(response,HttpStatus.OK);
		}
		response.setStatusCode(0);
		response.setStatusMessage("note is not present");
		return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
	}

	
	
	@RequestMapping(value="/note",method=RequestMethod.GET)
	public ResponseEntity<List<Note>> getAllNote(@RequestHeader("token") String token)
	{	
		List<Note> noteList=noteService.getAllNote(token);	
		System.out.println("return Note "+noteList);
		return new ResponseEntity<List<Note>>(noteList,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/note/{id}" , method=RequestMethod.DELETE)
	public ResponseEntity<Response> delteForeverNote(@PathVariable int id,@RequestHeader("token") String token)
	{
		noteService.deleteNote(id,token);
		response=new Response();
		response.setStatusCode(166);
		response.setStatusMessage("note deleted permenantly successfully");
//		System.out.println("before send "+trashNote);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	

	
	@RequestMapping(value="/note/archive/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Response> updateArchive(@PathVariable("id") int id,@RequestHeader("token") String token)
	{
		//noteService.updateArchive(note);
		System.out.println("archive");
	
		noteService.updateArchive(id,token);
		response=new Response();
		response.setStatusCode(166);
		response.setStatusMessage("note updated successfully");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/note/trash/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Response> deleteNote(@PathVariable int id,@RequestHeader("token") String token)
	{
		
		System.out.println("UPDATE trash note");
	
		noteService.trashUpdateNote(id,token);
		response=new Response();
		response.setStatusCode(166);
		response.setStatusMessage("note deleted successfully");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/note/pin/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Response> updatePin(@PathVariable int id,@RequestHeader("token") String token)
	{
		noteService.updatePin(id,token);
		response=new Response();
		response.setStatusCode(166);
		response.setStatusMessage("update pin successfully");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
}
}