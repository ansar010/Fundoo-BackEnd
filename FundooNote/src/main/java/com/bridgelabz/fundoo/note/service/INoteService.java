package com.bridgelabz.fundoo.note.service;

import java.util.List;

import com.bridgelabz.fundoo.note.model.Note;

public interface INoteService {
	boolean addNote(String token, Note note);

	// Note getNote(String token);

	List<Note> getAllNote(String token);

	boolean archiveNote(String token, int id);

	List<Note> getArchiveNote(String token);

	boolean updateArchive(int id, String token);

	boolean trashUpdateNote(int id, String token);

	List<Note> getTrashNote(String token);

	boolean colorUpdateNote(int id, String token, Note note);

	boolean deleteNote(int id, String token);

	boolean updateNote(Note note, String token);

	boolean updatePin(int id, String token);
}
