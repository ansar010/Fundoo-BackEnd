package com.bridgelabz.fundoo.note.dao;

import java.util.List;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.model.User;

public interface INoteDao {

	boolean saveNote(Note note);

	Note getNote(int id);

	boolean updateNote(Note note);

	List<Note> getAllNotes(long userId);

	Note getArchiveNote(User user);

	boolean deleteNote(Note note);
}
