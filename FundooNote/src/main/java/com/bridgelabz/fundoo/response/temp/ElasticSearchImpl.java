//package com.user.notesapi.search;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Stream;
//
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.MatchQueryBuilder;
//import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
//import org.elasticsearch.index.query.Operator;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.user.notesapi.entity.Notes;
//
//import lombok.Data;
//
//@Service
//public class ElasticSearchImpl implements ElasticService {
//
//	@Autowired
//	private RestHighLevelClient client;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@Override
//	public void save(Object message) {
//		Map dataMap = objectMapper.convertValue(message, Map.class);
//
//		IndexRequest indexRequest = new IndexRequest("notesdata", "notes").id(dataMap.get("id") + "").source(dataMap);
//		try {
//
//			IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	@Override
//	public void update(Notes updateNote)
//	{
//		UpdateRequest  updateRequest= new UpdateRequest("notesdata", "notes", updateNote.getId()+"");
//		Map dataMap = objectMapper.convertValue(updateNote, Map.class);
//		updateRequest.doc(dataMap);
//		try {
//		client.update(updateRequest,RequestOptions.DEFAULT);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	
//		
//	}
//	
//	@Override
//	public void delete(long id)
//	{
//		DeleteRequest deleteRequest=new DeleteRequest("notesdata", "notes", id+"");
//		try {
//		client.delete(deleteRequest, RequestOptions.DEFAULT);
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void search(String searchContent) {
//
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//
//		QueryBuilder queryBuilder = boolQueryBuilder.must(QueryBuilders.wildcardQuery("title", "ss*")); // ("*"+searchContent+"*").lenient(true))
//
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(queryBuilder);
//
//		SearchRequest searchRequest = new SearchRequest("notesdata").types("notes").source(searchSourceBuilder);
//		SearchResponse response = null;
//		try {
//			response = client.search(searchRequest, RequestOptions.DEFAULT);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		response.getHits().iterator();
//	}
//	
//	
//
//	@Override
//	public List<Notes> multipleFieldQuery(Map<String, Float> fields, String text, Map<String, Object> restrictions,
//			String type, String index) {
//		BoolQueryBuilder boolQueryBuilder = boolQuery(fields, text, restrictions);
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(boolQueryBuilder);
//
//		SearchRequest searchRequest = new SearchRequest("notesdata").types("notes").source(searchSourceBuilder);
//		SearchResponse response = null;
//		System.out.println(searchRequest.toString());
//		try {
//			response = client.search(searchRequest, RequestOptions.DEFAULT);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		//response.getHits().spliterator().forEachRemaining(System.out::println);
//		List<Notes> searchContent=new ArrayList<Notes>();
//		//response.getHits().spliterator()
//		response.getHits().spliterator().forEachRemaining(x ->{
//			try {
//				searchContent.add(objectMapper.readValue(x.getSourceAsString(), Notes.class));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
////		for(Notes notes:searchContent)
////		{
////			System.out.println(notes);
////		}
//		return searchContent;
//	}
//
//	private BoolQueryBuilder boolQuery(Map<String, Float> fields, String text, Map<String, Object> restrictions) {
//
//		if (!text.startsWith("*")) {
//			text ="*"+text;
//		}
//
//		if (!text.endsWith("*")) {
//			text =text+"*";
//		}
//
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
////		boolQueryBuilder.should(QueryBuilders.multiMatchQuery(text));
//		boolQueryBuilder.must(QueryBuilders.queryStringQuery(text).fields(fields));
//	
////text).lenient(true).fields(fields));
////	fields.forEach((fil,value)->{
////			boolQueryBuilder
////		});
//
//		restrictions.forEach((field, value) -> {
//			boolQueryBuilder.must(QueryBuilders.matchQuery(field, value).operator(Operator.AND));
//		});
//
//		return boolQueryBuilder;
//
//	}
//}
