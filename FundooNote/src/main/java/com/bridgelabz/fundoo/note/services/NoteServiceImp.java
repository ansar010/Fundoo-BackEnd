package com.bridgelabz.fundoo.note.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dao.INoteRepository;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("noteService")
@PropertySource("classpath:message.properties")
public class NoteServiceImp implements INoteService
{

	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private UserToken userToken;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Response createNote(NoteDTO noteDTO, String token) 
	{
	
		log.info("user note->"+noteDTO.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		//transfer DTO data into Model
		Note note = modelMapper.map(noteDTO, Note.class);
		
		Optional<User> user = userRepository.findById(userId);
		
		note.setUser(user.get());
		note.setCreateStamp(LocalDateTime.now());
		noteRepository.save(note);

		//		User user2 = note.getUser();
		//		System.out.println(user2.toString());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteCreate.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response updateNote(NoteDTO noteDTO, String token) 
	{
		log.info("user note->"+noteDTO.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		//transfer DTO data into Model
		Note note = modelMapper.map(noteDTO, Note.class);
//		User user = userRepository.findById(userId).orElse(th)
		
		if(note.getUser().getUserId()==userId)
		{
			noteRepository.save(note);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdate.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));
		}
		return null;
	}

	//	Note note;

//	@Override
//	public Response addNote(NoteDTO noteDTO, String token)
//	{
//		log.info("user note->"+noteDTO.toString());
//		log.info("Token->"+token);
//
//		long userId = userToken.tokenVerify(token);
//		log.info(Long.toString(userId));
//
//		//transfer DTO data into Model
//		Note note = modelMapper.map(noteDTO, Note.class);
//		
//		//user details
//		Optional<User> user = userRepository.findById(userId);
//			
//		note.setCreateStamp(LocalDateTime.now());
//		note.setUserId(user.get());
//		//add note to list in user table
//		user.get().getNotes().add(note);
//		userRepository.save(user.get());
//		
//		long userId2 = note.getUserId().getUserId();
//		System.out.println("Ansar"+userId2);
//		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteCreate.successMsg"),
//				Integer.parseInt(environment.getProperty("status.success.code")));
//
//		return response;
//
//	}
//
//	@Override
////	public Response updateNote(NoteDTO noteDTO, String token,long noteId) 
//	public Response updateNote(NoteDTO noteDTO, String token) 
//	{
//		log.info("user note->"+noteDTO.toString());
//		log.info("Token->"+token);
//
//		long userId = userToken.tokenVerify(token);
//		log.info(Long.toString(userId));
//		
//		Note note = modelMapper.map(noteDTO, Note.class);
//	
//		
//			Optional<User> user = userRepository.findById(userId);
////			if(userId==id)
////			{
////				note.setUpdateStamp(LocalDateTime.now());
////				
////				noteRepository.save(note);
////				
////				Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdate.successMsg"),
////						Integer.parseInt(environment.getProperty("status.success.code")));
//	//
////				return response;
////			}
//			
////			for (int i = 0; i < user.get().getNotes().size(); i++) {
////				if(note.getNoteId()==user.get().getNotes().iterator().hasNext().has)
////				{	
////					user.get().getNotes().add(note)
////				}
////			}
//			
////	
////		//transfer DTO data into Model
////		Note note = modelMapper.map(noteDTO, Note.class);
////		note.setNoteId(noteId);
////		
////		Optional<Note> dbNote = noteRepository.findById(note.getNoteId());
////		
////		User user = dbNote.get().getUserId();
////		
////		System.out.println("sdfsdf"+user.toString());
////		
////		long id = user.getUserId();
////	
//
//		
//		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdateError.successMsg"),
//				Integer.parseInt(environment.getProperty("status.noteUpdateError.errorcode")));
//		return response;
//	}
//
////	private List<Note> listOfNotes(long id)
////	{
////		log.info("Note id->"+id);
////
////		Optional<User> user = userRepository.findById(id);
////		List<Note> notes = user.get().getNotes();
////
////		log.info("List of Note ->"+notes.toString());
////
////		System.out.println("Notes --->"+notes.toString());
////
////		return notes;
////	}
//
//
//
//	//	@Override
//	//	public boolean updateNote(NoteDTO noteDTO, String token) 
//	//	{
//	//		log.info("user note->"+noteDTO.toString());
//	//		log.info("Token->"+token);
//	//	
//	//		long userId = userToken.tokenVerify(token);
//	//		
//	//		log.info(Long.toString(userId));
//	//		
//	//		Note note = modelMapper.map(noteDTO, Note.class);
//	//		Optional<User> userDeatils = userRepository.findById(userId);
//	//		
//	//		System.out.println(userDeatils.get());
//	//		
//	////		note.setUser(userDeatils.get());
//	//		note.setCreateStamp(LocalDate.now());
//	//		noteRepository.save(note);
//	//		
//	//		return true;
//	//	}
//

}
