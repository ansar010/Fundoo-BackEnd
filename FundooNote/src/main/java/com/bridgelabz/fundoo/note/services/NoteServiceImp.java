package com.bridgelabz.fundoo.note.services;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dao.INoteRepository;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.model.User;
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
	
	@Override
	public boolean addNote(NoteDTO noteDTO, String token)
	{
		log.info("user note->"+noteDTO.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		
		log.info(Long.toString(userId));
		
		Note note = modelMapper.map(noteDTO, Note.class);
		
		noteRepository.save(note);
		
////		Note note = modelMapper.map(noteDTO, Note.class);
//		
//		Optional<User> userDeatils = userRepository.findById(userId);
//		
//		System.out.println(userDeatils.get());
//		
////		note.setUser(userDeatils.get());
		note.setCreateStamp(LocalDate.now());
//		noteRepository.save(note);
		
		return true;
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
