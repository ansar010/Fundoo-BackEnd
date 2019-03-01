package com.bridgelabz.fundoo.note.services;

import java.util.List;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;

public interface INoteService {

	Response createNote(NoteDTO noteDTO, String token);

	Response updateNote(Note note, String token);

	Response deleteForever(long noteId, String token);

	Response trashStatus(long noteId, String token);

	Response pinStatus(long noteId, String token);

	Response archiveStatus(long noteId, String token);

	List<Note> getAllNote(String token);
	
	

//	public Response addNote(NoteDTO note , String token);

//	public Response updateNote(NoteDTO noteDTO, String token, long noteId);
//	public Response updateNote(NoteDTO noteDTO, String token);


//	public boolean updateNote(NoteDTO noteDTO, String token);
}
