package com.bridgelabz.fundoo.note.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LabelDTO 
{
	@NotEmpty
	private String labelName;
	
}
