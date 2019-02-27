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
public class NoteServiceImp implements INoteService{

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

//	Note note;
	
	@Override
	public Response addNote(NoteDTO noteDTO, String token)
	{
		log.info("user note->"+noteDTO.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);

		log.info(Long.toString(userId));
		
		//transferring DTO value to model
		Note note = modelMapper.map(noteDTO, Note.class);
	
		//list to store notes
		List<Note> notelist = new ArrayList<>();
	
		note.setCreateStamp(LocalDateTime.now());
		
		//save recently created note
		Note newNote = noteRepository.save(note);
		
		//getting previous list of notes
		List<Note> listOfNotes = listOfNotes(userId);
			
		
		notelist.add(newNote);
		notelist.addAll(listOfNotes);
		
		Optional<User> user = userRepository.findById(userId);
		
		user.get().setNotes(notelist);
		
		userRepository.save(user.get());
		
	
		Response response = StatusHelper.statusInfo(environment.getProperty("status.note.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;

	}
	
	private List<Note> listOfNotes(long id)
	{
		log.info("Note id->"+id);
		
		

		Optional<User> user = userRepository.findById(id);
		List<Note> notes = user.get().getNotes();
		
		log.info("List of Note ->"+notes.toString());

		System.out.println("Notes --->"+notes.toString());
		return notes;
	}

	//	@Override
	//	public boolean updateNote(NoteDTO noteDTO, String token) 
	//	{
	//		log.info("user note->"+noteDTO.toString());
	//		log.info("Token->"+token);
	//	
	//		long userId = userToken.tokenVerify(token);
	//		
	//		log.info(Long.toString(userId));
	//		
	//		Note note = modelMapper.map(noteDTO, Note.class);
	//		Optional<User> userDeatils = userRepository.findById(userId);
	//		
	//		System.out.println(userDeatils.get());
	//		
	////		note.setUser(userDeatils.get());
	//		note.setCreateStamp(LocalDate.now());
	//		noteRepository.save(note);
	//		
	//		return true;
	//	}


}
