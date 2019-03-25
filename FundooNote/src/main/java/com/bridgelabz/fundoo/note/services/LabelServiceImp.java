package com.bridgelabz.fundoo.note.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dao.ILabelRepository;
import com.bridgelabz.fundoo.note.dao.INoteRepository;
import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

import lombok.extern.slf4j.Slf4j;

@Service("LabelService")
@Slf4j
@PropertySource("classpath:message.properties")
public class LabelServiceImp implements ILabelService 
{
	@Autowired
	private Environment environment;

	@Autowired
	private ILabelRepository labelRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private UserToken userToken;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Response createLabel(@Valid LabelDTO labelDTO, String token) 
	{
		log.info("Service call");
		log.info("label detail->"+labelDTO.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);

		Optional<User> user = userRepository.findById(userId);

		Label label = modelMapper.map(labelDTO, Label.class);

		label.setUser(user.get());
		label.setCreateStamp(LocalDateTime.now());
		labelRepository.save(label);

		Response response = StatusHelper.statusInfo(environment.getProperty("status.labelCreate.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response updateLabel(long labelId, LabelDTO labelDTO, String token) 
	{
		log.info("Service call");
		log.info("label detail->"+labelDTO.toString());
		log.info("Token->"+token);
		log.info("label id->"+labelId);

		long userId = userToken.tokenVerify(token);

		Label label = modelMapper.map(labelDTO, Label.class);

		Optional<Label> dbLabel = labelRepository.findById(labelId);
		long dbuserId = dbLabel.get().getUser().getUserId();

		if(userId==dbuserId&&dbLabel.isPresent())
		{
			//by using below we can concatinate old data and new one
			//	String oldData = dbLabel.get().getLabelName();
			//	dbLabel.get().setLabelName(oldData+label.getLabelName());
			dbLabel.get().setUpdateStamp(LocalDateTime.now());
			dbLabel.get().setLabelName(label.getLabelName());
			labelRepository.save(dbLabel.get());

			Response response = StatusHelper.statusInfo(environment.getProperty("status.labelUpdate.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}

		Response response = StatusHelper.statusInfo(environment.getProperty("status.labelUpdate.errorMsg"),
				Integer.parseInt(environment.getProperty("status.error.code")));

		return response;
	}

	@Override
	public Response deleteLabel(long labelId, String token) 
	{
		log.info("Service call");
		log.info("Token->"+token);
		log.info("label id->"+labelId);

		long userId = userToken.tokenVerify(token);

		Optional<Label> dbLabel = labelRepository.findById(labelId);
		long dbUserId = dbLabel.get().getUser().getUserId();

		if(userId==dbUserId&&dbLabel.isPresent())
		{
			dbLabel.get().setDeleteStamp(LocalDateTime.now());
			labelRepository.delete(dbLabel.get());

			Response response = StatusHelper.statusInfo(environment.getProperty("status.labeldelete.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}

		Response response = StatusHelper.statusInfo(environment.getProperty("status.labeldelete.errorMsg"),
				Integer.parseInt(environment.getProperty("status.error.code")));

		return response;
	}

	@Override
	public List<Label> getAllLabel(String token) 
	{
		log.info("Service call");
		log.info("Token->"+token);
		long userId = userToken.tokenVerify(token);

		//		List<Label> listLabel = labelRepository.findAllById(userId).orElse(new ArrayList<Label>());

		List<Label> listLabel = labelRepository.findAllById(userId);

		return listLabel;
	}

	@Override
	public Response addLabelToNote(long labelId, String token, long noteId)
	{
		log.info("Service call");
		log.info("Token->"+token);
		log.info("label id->"+labelId);
		log.info("label id->"+noteId);

		long userId = userToken.tokenVerify(token);
		Optional<Note> note = noteRepository.findById(noteId);
		Optional<Label> label = labelRepository.findById(labelId);

		if(note.get().getUser().getUserId()==userId && label.get().getUser().getUserId()==userId)
		{
			note.get().getLabels().add(label.get());
			label.get().getNotes().add(note.get());
			noteRepository.save(note.get());
			labelRepository.save(label.get());

			Response response = StatusHelper.statusInfo(environment.getProperty("status.labeladdNote.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.labeladdNote.errorMsg"),
				Integer.parseInt(environment.getProperty("status.error.code")));

		return response;
	}

	@Override
	public Response deleteLabelfromNote(long labelId, String token, long noteId) {
		log.info("Service call");
		log.info("Token->"+token);
		log.info("label id->"+labelId);
		log.info("label id->"+noteId);

		long userId = userToken.tokenVerify(token);
		Optional<Note> note = noteRepository.findById(noteId);
		Optional<Label> label = labelRepository.findById(labelId);

		if(note.get().getUser().getUserId()==userId && label.get().getUser().getUserId()==userId)
		{
			note.get().getLabels().remove(label.get());
			label.get().getNotes().remove(note.get());
			noteRepository.save(note.get());
			labelRepository.save(label.get());

			Response response = StatusHelper.statusInfo(environment.getProperty("status.labeldeleteNote.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}
		return null;
	}



}
