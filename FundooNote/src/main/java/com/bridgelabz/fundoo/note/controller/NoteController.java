package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		//		log.info(requzest.getAttribute("token").toString());
		bindingResult(bindingResult);
		customValidation(noteDTO);

		Response response = noteService.createNote(noteDTO, token);

		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	//	/**
	//	 * An API to update note 
	//	 * @param noteDTO
	//	 * @param bindingResult
	//	 * @param token
	//	 * @return
	//	 */
	//	@PutMapping
	//	public ResponseEntity<Response> updateNote(@RequestHeader long noteId,@RequestBody NoteDTO noteDTO,BindingResult bindingResult,@RequestHeader("token") String token)
	//	{
	//		log.info("Note-->"+noteDTO);
	//		log.info("token-->"+token);
	//		
	//		bindingResult(bindingResult);
	////		customValidation(note);
	//		
	//		Response response = noteService.updateNote(noteId,noteDTO, token);
	//		
	//		return new ResponseEntity<>(response,HttpStatus.CREATED);
	//	}

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

	@DeleteMapping("/{noteId}")
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

	@GetMapping("/notelists")
	public ResponseEntity<List<Note>> getAllNoteList(@RequestHeader("token") String token,@RequestParam String isArchive,@RequestParam String isTrash)
	{
		log.info("token-->"+token);
		log.info("Get all note List called ");

		List<Note> listOfNotes= noteService.getAllNoteLists( token,isArchive,isTrash);

		return new ResponseEntity<>(listOfNotes,HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Note>> getAllNote(@RequestHeader("token") String token)
	{
		log.info("token-->"+token);


		List<Note> listOfNotes= noteService.getAllNote( token);

		return new ResponseEntity<>(listOfNotes,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/labeledNote")
	public ResponseEntity<List<Note>> getLabeledNote(@RequestHeader("token") String token,@RequestParam String labelName)
	{
		log.info("token-->"+token);
		log.info("labelName-->"+labelName);

		List<Note> listOfNotes= noteService.getlabeledNote(token,labelName);

		return new ResponseEntity<>(listOfNotes,HttpStatus.CREATED);
	}
	
	
//	@GetMapping("/labelnote")
//	public ResponseEntity<List<SendingNotes>> getLabeldNote(@RequestHeader String token,@RequestParam String label)throws NoteException
//	{
//		List<SendingNotes> notesall=noteServices.listLabelNotes(token,label);
//		return new ResponseEntity<List<SendingNotes>>(notesall,HttpStatus.OK);
//		
//}
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

	
	@PostMapping("/imageupload/{noteId}")
	public ResponseEntity<Response> saveImage(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file,@PathVariable String noteId)
	{
		log.info("token-->"+token);
		//log.info("file->"file);
		log.info("noteId->"+noteId);
		Response response = noteService.saveNoteImage(token,file,Long.valueOf(noteId));
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getnoteimage/{noteId}")
	public ResponseEntity<Resource> getNoteImage(@PathVariable String noteId)
	{
		//log.info("file->"file);
		log.info("noteId->"+noteId);
		Resource resource = noteService.getNoteImage(Long.valueOf(noteId));
//		return resource;
		return new ResponseEntity<>(resource,HttpStatus.OK);
	}
//	@GetMapping("/imageget/{id}")
//	public Resource getProfilePic(@PathVariable long id) throws NoteException
//	{
//		return noteServices.getNoteImage(Long.valueOf(id));	
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
		log.error("note empty validation");
		log.info(noteDTO.toString());
		if((noteDTO.getTitle()==null&&noteDTO.getDescription()==null)
				||noteDTO.getTitle().isEmpty()&&noteDTO.getDescription().isEmpty())
		{
			String statusMessge=environment.getProperty("status.note.validation");
			int statusCode=Integer.parseInt(environment.getProperty("status.noteValidation.errorCode"));

			throw new NoteException(statusMessge,statusCode);

		}
	}
}
