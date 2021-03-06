package com.bridgelabz.fundoo.note.services;

import java.util.List;
import java.util.Map;

import com.bridgelabz.fundoo.note.model.Note;

public interface ElasticSearchService {

	public void save(Note data);

	public void update(Note data);
	
	public void delete(long noteId);
	
	public List<Note> searchedNotes(String index,String type,Map<String,Float> fields,String searchText,long userId);

}
