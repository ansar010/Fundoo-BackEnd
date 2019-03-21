package com.bridgelabz.fundoo.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoteDTO
{
	private String title;
	private String description;
	private String color;
	
	@JsonProperty
	private boolean isPin;
}
