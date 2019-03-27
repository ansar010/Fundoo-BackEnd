//package com.user.notesapi.services;
//
//import java.util.List;
//
//import org.springframework.core.io.Resource;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.user.notesapi.dto.NotesDTO;
//import com.user.notesapi.dto.SendingNotes;
//import com.user.notesapi.entity.Notes;
//import com.user.notesapi.exception.NoteException;
//
//public interface NotesServices {
//
//	void createNote(String token, NotesDTO notesDTO) throws NoteException;
//
//	void updateNote(String token, Notes notes) throws NoteException;
//
//	void deleteNote(String token, long id) throws NoteException;
//
//	List<SendingNotes> listAllNotes(String token, String archive, String trash) throws NoteException;
//
//	List<Notes> matchedNotes(String token, String searchContent, boolean isArchive, boolean isTrash)
//			throws NoteException;
//
//	void updateNoteImage(long id, MultipartFile file);
//
//	Resource getNoteImage(long id);
//	List<SendingNotes> listLabelNotes(String token,String label) throws NoteException;
//}
