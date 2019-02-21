package com.bridgelabz.fundoo.note.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.model.User;

@Repository
public class NoteDao implements INoteDao
{

	@Autowired
	SessionFactory factory;
	
	@Override
	public boolean saveNote(Note note) 
	{
		if(factory!=null)
		{
			factory.getCurrentSession().save(note);
			System.out.println("after save "+note);

			System.out.println("note added successfull");
			return true;
		}
	//factory.getCurrentSession().save(user);
	return false;

	}

	@Override
	public Note getNote(int id) 
	{
		 Note note=(Note) factory.getCurrentSession().get(Note.class,id);
		 return note;

	}

	@Override
	public boolean updateNote(Note note) 
	{
		if(factory!=null)
		{
			factory.getCurrentSession().update(note);
			System.out.println("update  " +note);
			System.out.println("Note updated successfully");
			return true;
		}
		return false;
	}

	@Override
	public List<Note> getAllNotes(long userId) {
		if(factory!=null)
		{	
		
			  @SuppressWarnings("unchecked")
			List<Note> noteList =factory. getCurrentSession().createCriteria(Note.class).createCriteria("user").add(Restrictions.eq("userId", userId)).list();
			  System.out.println("get all notes call finish");
			  return noteList;
		}
		return null;
	}

	@Override
	public Note getArchiveNote(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteNote(Note note)
	{
		if(factory!=null)
		{
			System.out.println(note);
			factory.getCurrentSession().delete(note);
			return true;
		}
		return false;
	}

	
}