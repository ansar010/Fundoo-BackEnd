//
//package com.user.notesapi.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.user.notesapi.dto.NotesDTO;
//import com.user.notesapi.dto.SendingNotes;
//import com.user.notesapi.entity.Notes;
//import com.user.notesapi.exception.NoteException;
//import com.user.notesapi.response.Response;
//import com.user.notesapi.services.NotesServices;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 
// * @author 
// * @Purpose For CRUD Operation of Note And Searching Of Notes Through Elastic Search
// *
// */
//@RestController
//@RequestMapping("/api/notes")
//@CrossOrigin(origins= {"http://localhost:4200"},exposedHeaders= {"token"})
//@Slf4j
//public class NotesController {
//
//	 @Autowired
//	 NotesServices noteServices;
//	  
//	/**
//	 * 
//	 * @param notesDTO
//	 * @param token
//	 * @return
//	 * @throws NoteException
//	 */
//	@PostMapping
//	public ResponseEntity<Response> createNote(@RequestBody NotesDTO notesDTO,@RequestHeader("token")String token)throws NoteException,Exception
//	{
//		//RabbitMq Server Send
//		log.info("Rabbit Template Send");
//		
//		noteServices.createNote(token, notesDTO);
//		log.info("Data Send To DataBase");
//		Response response=new Response();
//		response.setStatusCode(166);
//		response.setStatusMessage("Note Created Successfully");
//		return new ResponseEntity<Response>(response,HttpStatus.OK);
//	}
//	
//	/**
//	 * 
//	 * @param notes
//	 * @param token
//	 * @return
//	 * @throws NoteException
//	 */
//	@PutMapping
//	public ResponseEntity<Response> updateNote(@RequestBody Notes notes,@RequestHeader("token")String token)throws NoteException
//	{ 
//		noteServices.updateNote(token,notes);
//		Response response=new Response();
//		response.setStatusCode(166);
//		response.setStatusMessage("Note Updated Successfully");
//		return new ResponseEntity<Response>(response,HttpStatus.OK);
//	}
//	/**
//	 * 
//	 * @param id
//	 * @param token
//	 * @return response whether Note Deleted or Not
//	 * @throws NoteException
//	 */
//	@DeleteMapping
//	public ResponseEntity<Response> deleteNote(@RequestParam int id,@RequestHeader("token")String token)throws NoteException
//	{
//		
//		noteServices.deleteNote(token, id);
//		Response response=new Response();
//		response.setStatusCode(166);
//		response.setStatusMessage("Note Deleted Successfully");
//		return new ResponseEntity<Response>(response,HttpStatus.OK);
//	}
//	
//	/**
//	 * 
//	 * @param token
//	 * @param archive
//	 * @param trash
//	 * @return Return All Notes Of A Particular Token
//	 * @throws NoteException
//	 */
//	@GetMapping
//	public /*ResponseEntity<List<Notes>> */ ResponseEntity<List<SendingNotes>> /*void*/ listAllNotes(@RequestHeader("token")String token,@RequestParam String archive,@RequestParam String trash)throws NoteException //@PathVariable("value") String value,
//	{
//		List<SendingNotes> notesall=noteServices.listAllNotes(token, archive, trash);
//		return new ResponseEntity<List<SendingNotes>>(notesall,HttpStatus.OK);
//		
//	} 
//	/**
//	 * @Purpose For Searching Notes With Give Words
//	 * @param token
//	 * @param text
//	 * @param isArchive
//	 * @param isTrash
//	 * @return List<Notes>
//	 * @throws NoteException
//	 */
//	@GetMapping("/search/{text}")
//	public ResponseEntity<List<Notes>> searchNotes(@RequestHeader("token")String token,
//	       @PathVariable String text,@RequestParam String isArchive,@RequestParam String isTrash) throws NoteException
//	{
//		List<Notes> list=noteServices.matchedNotes(token,text,Boolean.valueOf(isArchive),Boolean.valueOf(isTrash));
//		return new ResponseEntity<List<Notes>>(list,HttpStatus.OK);
//	}
//	
//	@PostMapping("/imageupload/{id}")
//	public ResponseEntity<Response> noteImageSave(@RequestHeader("token") String token,@RequestParam("file") MultipartFile file,@PathVariable String id) throws NoteException 
//	{	
//		System.out.println("helo");
//		noteServices.updateNoteImage(Long.valueOf(id), file);
//		Response response = new Response();
//		response.setStatusCode(166);
//		response.setStatusMessage("Image Uploaded");
//		return new ResponseEntity<Response>(response,HttpStatus.OK);
//	}
//	
//	@GetMapping("/imageget/{id}")
//	public Resource getProfilePic(@PathVariable long id) throws NoteException
//	{
//		return noteServices.getNoteImage(Long.valueOf(id));	
//	}
//	
//	@GetMapping("/labelnote")
//	public ResponseEntity<List<SendingNotes>> getLabeldNote(@RequestHeader String token,@RequestParam String label)throws NoteException
//	{
//		List<SendingNotes> notesall=noteServices.listLabelNotes(token,label);
//		return new ResponseEntity<List<SendingNotes>>(notesall,HttpStatus.OK);
//		
//	}
//	
//	
//}
