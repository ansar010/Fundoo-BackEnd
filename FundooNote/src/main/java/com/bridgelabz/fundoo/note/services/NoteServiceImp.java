package com.bridgelabz.fundoo.note.services;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteException;
import com.bridgelabz.fundoo.utility.UserToken;

public class NoteServiceImp implements INoteService{

	@Override
	public boolean addNote(NoteDTO note, String token) throws NoteException
	{
		//long userId = UserToken.tokenVerify(token);
		return false;
	}

}
