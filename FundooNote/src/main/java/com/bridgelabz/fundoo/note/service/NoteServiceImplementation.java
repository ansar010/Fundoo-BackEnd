package com.bridgelabz.fundoo.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoo.note.dao.INoteDao;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.utility.UserToken;

@Transactional
public class NoteServiceImplementation implements INoteService
{

	@Autowired
	INoteDao noteDao;
//	@Autowired
//	IUserServices userService;
	@Autowired
	IUserRepository userRepository;
	@Override
	public boolean addNote(String token,Note note) 
	{
		
		try {
			long id=UserToken.tokenVerify(token);
			System.out.println("entered id is "+id);
			Optional<User> user = userRepository.findById(id);
			if(user!=null)
			{
				note.setUser(user.get());
				boolean check=noteDao.saveNote(note);
					if(check)
						return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

//	@Override
//	public Note getNote(String token) 
//	{
//		try {
//			int id=UserToken.tokenVerify(token);
//			System.out.println("entered id is "+id);
//			User user=userService.getUser(id);
//			
//			
//			Note userNote=noteDao.getNote(user);
//			return userNote;
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	@Override
	public List<Note> getAllNote(String token) 
	{
		try {
			long userId=UserToken.tokenVerify(token);
			List<Note> userNoteList=noteDao.getAllNotes(userId);

			return userNoteList;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean archiveNote(String token, int id) 
	{
//		try {
//			int userId=UserToken.tokenVerify(token);
//			System.out.println("entered id is "+id);
//			User user=userService.getUser(id);
//			Note note=noteDao.getNote(id);
//			if(userId==note.getUser().getUserId())
//			{
//				note.setArchive(true);
//				boolean check=noteDao.
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return false;
	}
	@Override
	public List<Note> getArchiveNote(String token) {
		try {
			long id=UserToken.tokenVerify(token);
			System.out.println("entered id is "+id);
			
			Optional<User> user = userRepository.findById(id);

			
			
			
			List<Note> userNoteList=noteDao.getAllNotes(user.get().getId());
			System.out.println("userNote "+userNoteList);
			List<Note> archiveNotes=new ArrayList<>();
//			int index=0;
			for (int i = 0; i < userNoteList.size(); i++) 
			{
				if(userNoteList.get(i).isArchive()==true)
				{
					archiveNotes.add(userNoteList.get(i));
				}
			}
			System.out.println(archiveNotes);
			return archiveNotes;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		return null;
	}
	@Override
	public boolean updateArchive(int id,String token) 
	{
		
		try {
			long userId = UserToken.tokenVerify(token);
			Note note=noteDao.getNote(id);
			if(userId==note.getUser().getId())
			{
				System.out.println(note.isArchive());
				if(note.isArchive()==false)
					note.setArchive(true);
				else
					note.setArchive(false);
		
				noteDao.updateNote(note);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}
	@Override
	public boolean trashUpdateNote(int id,String token) 
	{
		try {
			long userId = UserToken.tokenVerify(token);
			Note note=noteDao.getNote(id);
			if(userId==note.getUser().getId())
			{
					if(note.isTrash()==false)
						note.setTrash(true);
					else
						note.setTrash(false);
				noteDao.updateNote(note);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public boolean updatePin(int id, String token) {
		
		try {
			long userId = UserToken.tokenVerify(token);
			Note note=noteDao.getNote(id);
			if(userId==note.getUser().getId())
			{
					if(note.isPin()==false)
						note.setPin(true);
					else
						note.setPin(false);
				noteDao.updateNote(note);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	
	@Override
	public List<Note> getTrashNote(String token) {
		try {
			long id=UserToken.tokenVerify(token);
			System.out.println("entered id is "+id);
			Optional<User> user = userRepository.findById(id);
			
			
			List<Note> userNoteList=noteDao.getAllNotes(user.get().getId());
			System.out.println("userNote "+userNoteList);
			List<Note> trashNote=new ArrayList<>();
//			int index=0;
			for (int i = 0; i < userNoteList.size(); i++) 
			{
				if(userNoteList.get(i).isTrash()==true)
				{
					trashNote.add(userNoteList.get(i));
				}
			}
			System.out.println(trashNote);
			return trashNote;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		return null;
	}
	@Override
	public boolean colorUpdateNote(int id,String token,Note note) {
//		noteDao.updateNote(note);
		return true;
	}
	@Override
	public boolean deleteNote(int id,String token) 
	{
		try {
			long userId=UserToken.tokenVerify(token);
			Note note=noteDao.getNote(id);
			if(userId==note.getUser().getId())
			{
				System.out.println(note.getId());
			boolean check=noteDao.deleteNote(note);
		
			return check;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return false;
	}
	@Override
	public boolean updateNote(Note note,String token) 
	{
		try {
			long userId=UserToken.tokenVerify(token);
		
			
			if(userId==note.getUser().getId())
			{
				boolean check=noteDao.updateNote(note);
				return check;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	
}
