package com.bridgelabz.fundoo.note.services;

import com.bridgelabz.fundoo.note.dto.NoteDTO;

public interface INoteService {

	public boolean addNote(NoteDTO note , String token);

//	public boolean updateNote(NoteDTO noteDTO, String token);
}
