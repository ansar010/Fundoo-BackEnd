package com.bridgelabz.fundoo.note.services;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.response.Response;

public interface INoteService {

	public Response addNote(NoteDTO note , String token);

//	public boolean updateNote(NoteDTO noteDTO, String token);
}
