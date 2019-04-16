package com.bridgelabz.fundoo.note.services;

import com.bridgelabz.fundoo.note.model.Note;

public interface ElasticSearchService {

	public void save(Note data);

	public void update(Note data);
	
	public void delete(long noteId);

}
