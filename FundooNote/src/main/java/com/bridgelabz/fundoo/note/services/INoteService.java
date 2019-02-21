package com.bridgelabz.fundoo.note.services;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.exception.NoteException;

public interface INoteService {

	public boolean addNote(NoteDTO note , String token) throws NoteException;
}
