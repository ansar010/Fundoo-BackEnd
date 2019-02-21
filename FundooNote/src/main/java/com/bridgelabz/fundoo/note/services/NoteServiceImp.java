package com.bridgelabz.fundoo.note.services;

import java.io.UnsupportedEncodingException;

import org.modelmapper.ModelMapper;

import com.bridgelabz.fundoo.note.dao.INoteRepository;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteException;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.utility.UserToken;

public class NoteServiceImp implements INoteService{

	INoteRepository noteRepository;
	
	ModelMapper modelMapper;
	
	@Override
	public boolean addNote(NoteDTO noteDTO, String token) throws NoteException, IllegalArgumentException, UnsupportedEncodingException
	{
		long userId = UserToken.tokenVerify(token);
		Note note = modelMapper.map(noteDTO, Note.class);
		
		note.setUser(note.getUser());
		noteRepository.save(note);
		return false;
	}

}
