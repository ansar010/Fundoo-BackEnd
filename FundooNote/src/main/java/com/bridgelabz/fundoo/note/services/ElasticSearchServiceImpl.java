package com.bridgelabz.fundoo.note.services;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RestHighLevelClient client;

	@Override
	public void save(Note note) 
	{	
		// Converting object into map
		@SuppressWarnings("rawtypes")
		Map noteMapper = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest("fundoo_note", "note_info", noteMapper.get("id")+"").source(noteMapper);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Note note) 
	{	
		UpdateRequest updateRequest = new UpdateRequest("fundoo_note", "note_info",Long.toString(note.getId()));
		@SuppressWarnings("rawtypes")
		Map noteMapper = objectMapper.convertValue(note, Map.class );
		updateRequest.doc(noteMapper);
		try {
			client.update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(long noteId) 
	{
		DeleteRequest deleteRequest = new DeleteRequest("fundoo_note", "note_info",Long.toString(noteId));
		try {
			client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
