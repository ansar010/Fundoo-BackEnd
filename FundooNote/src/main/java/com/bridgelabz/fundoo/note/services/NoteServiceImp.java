package com.bridgelabz.fundoo.note.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dao.INoteRepository;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.utility.UserToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("noteService")
public class NoteServiceImp implements INoteService{

	@Autowired
	INoteRepository noteRepository;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public boolean addNote(NoteDTO noteDTO, String token)
	{
		log.info("user note->"+noteDTO.toString());
		log.info("Token->"+token);

		long userId = UserToken.tokenVerify(token);
		
		log.info(Long.toString(userId));
		
		Note note = modelMapper.map(noteDTO, Note.class);
		Optional<User> userDeatils = userRepository.findById(userId);
		
		System.out.println(userDeatils.get());
		
		note.setUser(userDeatils.get());
		noteRepository.save(note);
		return true;
	}

}
