package com.bridgelabz.fundoo.note.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelDTO 
{
	@NotEmpty
	private String labelName;
	
}
