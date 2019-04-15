package com.bridgelabz.fundoo.note.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.note.model.Note;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ElasticDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Note data;
	private String type;
}
