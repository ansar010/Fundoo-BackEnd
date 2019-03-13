package com.bridgelabz.fundoo.note.services;

import javax.validation.Valid;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.response.Response;

public interface ILabelService {

	Response createLabel(@Valid LabelDTO labelDTO, String token);

	Response updateLabel(long labelId, LabelDTO labelDTO, String token);

	Response deleteLabel(long labelId, String token);	
	
	
}
