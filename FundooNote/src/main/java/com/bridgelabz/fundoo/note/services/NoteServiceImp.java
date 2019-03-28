package com.bridgelabz.fundoo.note.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.note.dao.ILabelRepository;
import com.bridgelabz.fundoo.note.dao.INoteRepository;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.dao.IUserRepository;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("noteService")
@PropertySource("classpath:message.properties")
public class NoteServiceImp implements INoteService
{
	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private ILabelRepository labelRepository;

	@Autowired
	private UserToken userToken;

	@Autowired
	private ModelMapper modelMapper;

	private final Path fileLocation = Paths.get("/home/admin1/FundooFile");
//	private final Path fileLocation = Paths.get("G:\\FundooFile");

	@Override
	public Response createNote(NoteDTO noteDTO, String token) 
	{

		log.info("user note->"+noteDTO.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));
		System.out.println(noteDTO.isPin());
		//transfer DTO data into Model
		Note note = modelMapper.map(noteDTO, Note.class);
		System.out.println(noteDTO.getTitle());
		Optional<User> user = userRepository.findById(userId);

		note.setUser(user.get());
		note.setCreateStamp(LocalDateTime.now());
		noteRepository.save(note);

		//		User user2 = note.getUser();
		//		System.out.println(user2.toString());
		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteCreate.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response updateNote(Note note, String token) 
	{
		log.info("user note->"+note.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		//User user = userRepository.findById(userId).orElse(th)
		note.setUpdateStamp(LocalDateTime.now());

		Optional<Note> dbNote = noteRepository.findById(note.getId());

		long dbUserId = dbNote.get().getUser().getUserId();

		if(dbUserId==userId&&dbNote.isPresent())
		{

			noteRepository.save(note);
			Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdate.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdateError.errorMsg"),
				Integer.parseInt(environment.getProperty("status.noteUpdateError.errorcode")));
		return response;	
	}

	//	@Override
	//	public Response updateNote(long noteId,NoteDTO noteDTO, String token) {
	//		log.info("note id->"+noteId);
	//		log.info("noteDto->"+noteDTO);
	//		log.info("token->"+token);
	//
	//		long userId = userToken.tokenVerify(token);
	//		//		Note note = modelMapper.map(noteDTO, Note.class);
	//
	//		Optional<Note> dbNote = noteRepository.findById(noteId);
	//		long dbUserId = dbNote.get().getUser().getUserId();
	//
	//		if(dbUserId==userId&&dbNote.isPresent())
	//		{
	//			//			note.setUpdateStamp(LocalDateTime.now());
	//			//			note.setId(noteId);
	//			//			Note status = noteRepository.save(note);
	//			dbNote.get().setUpdateStamp(LocalDateTime.now());
	//			dbNote.get().setTitle(noteDTO.getTitle());
	//			dbNote.get().setDescription(noteDTO.getDescription());
	//			dbNote.get().setColor(noteDTO.getColor());
	//			Note status = noteRepository.save(dbNote.get());
	//			if(status.equals(null))
	//			{
	//				Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdateError.errorMsg"),
	//						Integer.parseInt(environment.getProperty("status.noteUpdateError.errorcode")));
	//				return response;	
	//			}
	//			Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdate.successMsg"),
	//					Integer.parseInt(environment.getProperty("status.success.code")));
	//
	//			return response;
	//
	//		}
	//
	//		return null;
	//	}

	@Override
	public List<Note> getAllNoteLists(String token, String isArchive, String isTrash) {

		log.info("Token->"+token);
		log.info("isArchive->"+isArchive);
		log.info("isTrash->"+isTrash);

		long userId = userToken.tokenVerify(token);

		Optional<List<Note>> list_of_notes = noteRepository.findAllById(userId, Boolean.valueOf(isArchive),Boolean.valueOf(isTrash));

		return list_of_notes.get();
	}


	@Override
	public Response deleteForever(long noteId, String token)
	{
		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);
		long dbuserId = note.get().getUser().getUserId();

		if(note.isPresent()&&dbuserId==userId&&note.get().isTrash()==true)
		{
			noteRepository.delete(note.get());

			Response response = StatusHelper.statusInfo(environment.getProperty("status.deleteForever.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.deleteForever.errorMsg"),
				Integer.parseInt(environment.getProperty("status.deleteForever.errorCode")));
		return response;
	}

	@Override
	public Response trashStatus(long noteId, String token) 
	{

		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);
		long dbuserId = note.get().getUser().getUserId();

		if(dbuserId==userId)
		{
			if(note.get().isTrash()==true)
			{
				note.get().setTrash(false);
				noteRepository.save(note.get());

				Response response = StatusHelper.statusInfo(environment.getProperty("status.restore.successMsg"),
						Integer.parseInt(environment.getProperty("status.success.code")));

				return response;
			}
			else
			{
				note.get().setTrash(true);
			}
			noteRepository.save(note.get());




		}
		//		Response response = StatusHelper.statusInfo(environment.getProperty("status.moveTrash.errorMsg"),
		//				Integer.parseInt(environment.getProperty("status.deleteForever.errorCode")));
		//		return response;
		Response response = StatusHelper.statusInfo(environment.getProperty("status.moveTrash.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response pinStatus(long noteId, String token)
	{

		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);
		long dbuserId = note.get().getUser().getUserId();

		if(dbuserId==userId)
		{
			if(note.get().isPin()==true)
			{
				note.get().setPin(false);

				noteRepository.save(note.get());

				Response response = StatusHelper.statusInfo(environment.getProperty("status.unpin.successMsg"),
						Integer.parseInt(environment.getProperty("status.success.code")));

				return response;
			}
			else
			{
				note.get().setPin(true);
			}
			noteRepository.save(note.get());
		}
		//		Response response = StatusHelper.statusInfo(environment.getProperty("status.moveTrash.errorMsg"),
		//				Integer.parseInt(environment.getProperty("status.deleteForever.errorCode")));
		//		return response;
		Response response = StatusHelper.statusInfo(environment.getProperty("status.pinned.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response archiveStatus(long noteId, String token) 
	{


		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);
		long dbuserId = note.get().getUser().getUserId();

		if(dbuserId==userId)
		{
			if(note.get().isArchive()==true)
			{
				note.get().setArchive(false);

				noteRepository.save(note.get());

				Response response = StatusHelper.statusInfo(environment.getProperty("status.unarchive.successMsg"),
						Integer.parseInt(environment.getProperty("status.success.code")));

				return response;
			}
			else
			{
				note.get().setArchive(true);
			}
			noteRepository.save(note.get());
		}
		//		Response response = StatusHelper.statusInfo(environment.getProperty("status.moveTrash.errorMsg"),
		//				Integer.parseInt(environment.getProperty("status.deleteForever.errorCode")));
		//		return response;
		Response response = StatusHelper.statusInfo(environment.getProperty("status.archive.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public List<Note> getAllNote(String token) 
	{
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		//		List<Note> noteRepository.findByUser_id(userId);

		List<Note> listNote = noteRepository.findAll();

		List<Note> list = new ArrayList<>();

		for (int i = 0; i < listNote.size(); i++) {
			if(listNote.get(i).getUser().getUserId()==userId)
			{
				list.add(listNote.get(i));
			}
		}

		return list;
		//		List<Note> notes = noteRepository.findAllByUserId(userId);
		//		System.out.println("notes"+notes);

	}


	@Override
	public List<Note> getlabeledNote(String token, String labelName) {

		//		Optional<Long> labelId = labelRepository.findIdByLabelName(labelName);
		Optional<Label> label = labelRepository.findBylabelName(labelName);

		long userId = userToken.tokenVerify(token);

		if(label.get().getUser().getUserId()==userId)
		{
			List<Note> labeledNotesList = labelRepository.findById(label.get().getId()).get().getNotes().stream().collect(Collectors.toList());
			return labeledNotesList;
		}
		return null;
	}

	//method to store file 
	@Override
	public Response saveNoteImage(String token, MultipartFile file, Long noteId) {

		Note note = noteRepository.findById(noteId).get();

		long userId = userToken.tokenVerify(token);

		// generate random universal unique id 
		UUID uuid = UUID.randomUUID();

		String uniqueStringId = uuid.toString();

		//copying file stream in target file
		try {
			Files.copy(file.getInputStream(), fileLocation.resolve(uniqueStringId),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(note.getUser().getUserId()==userId)
		{
			note.setImage(uniqueStringId);
			noteRepository.save(note);

			Response response = StatusHelper.statusInfo(environment.getProperty("status.imageUpload.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;

		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.imageUpload.errorMsg"),
				Integer.parseInt(environment.getProperty("status.error.code")));

		return response;

	}	



	@Override
	public Resource getNoteImage(Long noteId) {

		Note note = noteRepository.findById(noteId).get();

		//validating user 
//		if(note.getUser().getUserId()==userId)
//		{
			// get image name from database
			Path imagePath = fileLocation.resolve(note.getImage());

			try {
				//creating url resource based on uri object
				Resource resource = new UrlResource(imagePath.toUri());
				if(resource.exists() || resource.isReadable())
				{
					return resource;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
//		}
		return null;
	}

	//	@Override
	//	public List<SendingNotes> listLabelNotes(String token,String label)throws NoteException {
	//		long labelId=labelRepository.findIdByLabelName(label).get();
	//			TokenVerify.tokenVerifing(token);						
	//		List<Notes> list=labelRepository.findById(labelId).get().getNotes().stream().collect(Collectors.toList());												
	//		List<SendingNotes> xyz = new ArrayList<SendingNotes>();
	//		list.stream().forEach( x -> xyz.add(new SendingNotes(x,  new ArrayList<CollabUserDetails>())));
	//		return xyz;
	//}
	//	Note note;

	//	@Override
	//	public Response addNote(NoteDTO noteDTO, String token)
	//	{
	//		log.info("user note->"+noteDTO.toString());
	//		log.info("Token->"+token);
	//
	//		long userId = userToken.tokenVerify(token);
	//		log.info(Long.toString(userId));
	//
	//		//transfer DTO data into Model
	//		Note note = modelMapper.map(noteDTO, Note.class);
	//		
	//		//user details
	//		Optional<User> user = userRepository.findById(userId);
	//			
	//		note.setCreateStamp(LocalDateTime.now());
	//		note.setUserId(user.get());
	//		//add note to list in user table
	//		user.get().getNotes().add(note);
	//		userRepository.save(user.get());
	//		
	//		long userId2 = note.getUserId().getUserId();
	//		System.out.println("Ansar"+userId2);
	//		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteCreate.successMsg"),
	//				Integer.parseInt(environment.getProperty("status.success.code")));
	//
	//		return response;
	//
	//	}
	//
	//	@Override
	////	public Response updateNote(NoteDTO noteDTO, String token,long noteId) 
	//	public Response updateNote(NoteDTO noteDTO, String token) 
	//	{
	//		log.info("user note->"+noteDTO.toString());
	//		log.info("Token->"+token);
	//
	//		long userId = userToken.tokenVerify(token);
	//		log.info(Long.toString(userId));
	//		
	//		Note note = modelMapper.map(noteDTO, Note.class);
	//	
	//		
	//			Optional<User> user = userRepository.findById(userId);
	////			if(userId==id)
	////			{
	////				note.setUpdateStamp(LocalDateTime.now());
	////				
	////				noteRepository.save(note);
	////				
	////				Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdate.successMsg"),
	////						Integer.parseInt(environment.getProperty("status.success.code")));
	//	//
	////				return response;
	////			}
	//			
	////			for (int i = 0; i < user.get().getNotes().size(); i++) {
	////				if(note.getNoteId()==user.get().getNotes().iterator().hasNext().has)
	////				{	
	////					user.get().getNotes().add(note)
	////				}
	////			}
	//			
	////	
	////		//transfer DTO data into Model
	////		Note note = modelMapper.map(noteDTO, Note.class);
	////		note.setNoteId(noteId);
	////		
	////		Optional<Note> dbNote = noteRepository.findById(note.getNoteId());
	////		
	////		User user = dbNote.get().getUserId();
	////		
	////		System.out.println("sdfsdf"+user.toString());
	////		
	////		long id = user.getUserId();
	////	
	//
	//		
	//		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdateError.successMsg"),
	//				Integer.parseInt(environment.getProperty("status.noteUpdateError.errorcode")));
	//		return response;
	//	}
	//
	////	private List<Note> listOfNotes(long id)
	////	{
	////		log.info("Note id->"+id);
	////
	////		Optional<User> user = userRepository.findById(id);
	////		List<Note> notes = user.get().getNotes();
	////
	////		log.info("List of Note ->"+notes.toString());
	////
	////		System.out.println("Notes --->"+notes.toString());
	////
	////		return notes;
	////	}
	//
	//
	//
	//	//	@Override
	//	//	public boolean updateNote(NoteDTO noteDTO, String token) 
	//	//	{
	//	//		log.info("user note->"+noteDTO.toString());
	//	//		log.info("Token->"+token);
	//	//	
	//	//		long userId = userToken.tokenVerify(token);
	//	//		
	//	//		log.info(Long.toString(userId));
	//	//		
	//	//		Note note = modelMapper.map(noteDTO, Note.class);
	//	//		Optional<User> userDeatils = userRepository.findById(userId);
	//	//		
	//	//		System.out.println(userDeatils.get());
	//	//		
	//	////		note.setUser(userDeatils.get());
	//	//		note.setCreateStamp(LocalDate.now());
	//	//		noteRepository.save(note);
	//	//		
	//	//		return true;
	//	//	}
	//

}
