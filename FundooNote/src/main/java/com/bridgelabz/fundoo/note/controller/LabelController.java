package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.exception.LabelException;
import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.services.ILabelService;
import com.bridgelabz.fundoo.response.Response;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PropertySource("classpath:message.properties")
@RestController
@CrossOrigin(origins = "*" ,allowedHeaders = "*")

@RequestMapping("/user/label")
public class LabelController 
{
	@Autowired
	Environment environment;
	
	@Autowired
	ILabelService lableService;

	@PostMapping
	@ApiOperation(value="This api for create label...")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDTO labelDTO, @RequestHeader String token)
	{
		log.info("Label-->"+labelDTO);
		log.info("token-->"+token);
	
		isEmptyLabel(labelDTO);
		Response response = lableService.createLabel(labelDTO,token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PutMapping
	@ApiOperation(value="This api for update label...")
	public ResponseEntity<Response> updateLabel(@RequestParam long labelId,@RequestBody LabelDTO labelDTO, @RequestHeader String token)
	{
		log.info("Label-->"+labelDTO);
		log.info("token-->"+token);
		
		isEmptyLabel(labelDTO);
		Response response = lableService.updateLabel(labelId,labelDTO,token);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@DeleteMapping
	@ApiOperation(value="This api for delete label...")
	public ResponseEntity<Response> deleteLabel(@RequestParam long labelId, @RequestHeader String token)
	{
		log.info("token-->"+token);
	
		Response response = lableService.deleteLabel(labelId,token);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	
	@GetMapping
	@ApiOperation(value="This api for get all labels...")
	public List<Label> getAllLabel(@RequestHeader String token)
	{
		log.info("token-->"+token);
	
		 List<Label> allLabel = lableService.getAllLabel(token);
	
		return  allLabel;
	}
	
	@PostMapping("/addLabeltonote")
	@ApiOperation(value="This api for add label into note...")
	public ResponseEntity<Response> addLabelToNote(@RequestParam long labelId, @RequestParam long noteId, @RequestHeader("token") String token )
	{
		log.info("token-->"+token);
		log.info("labelId-->"+labelId);
		log.info("noteId-->"+noteId);

		Response response = lableService.addLabelToNote(labelId,token,noteId);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@PostMapping("/deleteLabelfromnote")
	@ApiOperation(value="This api for delete label from note...")
	public ResponseEntity<Response> deleteLabelfromNote(@RequestParam long labelId, @RequestHeader String token , long noteId)
	{
		log.info("token-->"+token);
		log.info("labelId-->"+labelId);
		log.info("noteId-->"+noteId);

		Response response = lableService.deleteLabelfromNote(labelId,token,noteId);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	private void isEmptyLabel(LabelDTO labelDTO)
	{
		log.info("label details"+labelDTO.toString());
		log.error("label empty validation");

		if(labelDTO.getLabelName().isEmpty()||labelDTO.getLabelName()==null) {
			
			String statusMessge=environment.getProperty("status.label.validation");
			
			int statusCode=Integer.parseInt(environment.getProperty("status.labelValidation.errorCode"));
			
			throw new LabelException(statusMessge,statusCode);
		}
	}
	
	
}
