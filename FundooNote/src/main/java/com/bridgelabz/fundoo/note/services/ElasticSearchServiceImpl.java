package com.bridgelabz.fundoo.note.services;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	RestHighLevelClient client;
	
	@Override
	public void save(Object data) {
		
		// Converting object into map
		Map<String ,Object> noteMapper = objectMapper.convertValue(data, Map.class);
		IndexRequest indexRequest = new IndexRequest("fundoo_note", "note_info", noteMapper.get("id")+"").source(noteMapper);
		try {
//			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			client.index(indexRequest, RequestOptions.DEFAULT);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
