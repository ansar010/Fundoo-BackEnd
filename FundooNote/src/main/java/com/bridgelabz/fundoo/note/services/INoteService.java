package com.bridgelabz.fundoo.note.services;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.response.Response;

public interface INoteService {

	Response createNote(NoteDTO noteDTO, String token);

	Response updateNote(NoteDTO noteDTO, String token);

//	public Response addNote(NoteDTO note , String token);

//	public Response updateNote(NoteDTO noteDTO, String token, long noteId);
//	public Response updateNote(NoteDTO noteDTO, String token);


//	public boolean updateNote(NoteDTO noteDTO, String token);
}
