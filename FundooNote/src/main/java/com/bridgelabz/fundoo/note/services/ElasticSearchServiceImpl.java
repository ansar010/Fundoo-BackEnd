package com.bridgelabz.fundoo.note.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.lucene.search.TermQuery;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RestHighLevelClient client;
	
	@Autowired
	IUserRepository userRespository;
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
	public List<Note> searchedNotes(String index, String type, Map<String, Float> fields, String searchText,long userId) {
		
		SearchRequest searchRequest = new SearchRequest(index).types(type);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQuery = boolQuery(fields, searchText, userId);
		searchSourceBuilder.query(boolQuery);
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = null;
		System.out.println(searchRequest.toString());
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Note> listOfSearchedNotes = new ArrayList<>();
		
		searchResponse.getHits().spliterator().forEachRemaining(note->{
			try {
				listOfSearchedNotes.add(objectMapper.readValue(note.getSourceAsString(), Note.class));
			} catch (IOException e) {
				e.printStackTrace();
			}});
//		searchSourceBuilder.query()
		
		return listOfSearchedNotes;
	}

	// method to write search bool query
	private BoolQueryBuilder boolQuery(Map<String, Float> fields,String textToSearch, long userId)
	{
		// concatenating wild-card to search text
		if(!textToSearch.startsWith("*")) {
			textToSearch = "*"+textToSearch;
		}
		
		if(!textToSearch.endsWith("*")) {
			textToSearch = textToSearch+"*";
		}
		
		Optional<User> findById = userRespository.findById(1l);
		System.out.println(findById.get().toString());
		BoolQueryBuilder searchQuery = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery(textToSearch).fields(fields))
				.filter(QueryBuilders.termQuery("user.userId", userId));
//		BoolQueryBuilder searchQuery = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery(textToSearch).fields(fields));
		
		return searchQuery;
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
