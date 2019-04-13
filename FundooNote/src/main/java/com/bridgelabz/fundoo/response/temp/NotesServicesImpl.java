//package com.user.notesapi.services;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.function.Consumer;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.user.notesapi.appconfig.ApplicationConfiguration;
//import com.user.notesapi.dto.CollabUserDetails;
//import com.user.notesapi.dto.NotesDTO;
//import com.user.notesapi.dto.SendingNotes;
//import com.user.notesapi.entity.Labels;
//import com.user.notesapi.entity.Notes;
//import com.user.notesapi.exception.NoteException;
//import com.user.notesapi.repository.CollabRepository;
//import com.user.notesapi.repository.LabelsRepository;
//import com.user.notesapi.repository.NotesRepository;
//import com.user.notesapi.search.ElasticService;
//import com.user.notesapi.util.TokenVerify;
//
//@Service
//@PropertySource("classpath:application.properties")
//public class NotesServicesImpl implements NotesServices {
//
//	@Autowired
//	private ModelMapper modelMapper;
//
//	@Autowired
//	private NotesRepository notesRepository;
//
//	@Autowired
//	private LabelsRepository labelRepository;
//
//	@Autowired
//	private CollaboratorService collabservice;
//
//	@Autowired
//	private CollabRepository collabRepo;
//
//	@Autowired
//	private ElasticService elasticService;
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	@Autowired
//	private ElasticService service;
//
//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Value("${spring.ROOT_URI}")
//	private String ROOT_URI;
//
//	private final Path rootLocation = Paths.get("/home/administrator/Desktop/upload-files");
//
//	public void createNote(String token, NotesDTO notesDTO) throws NoteException {
//		long id = TokenVerify.tokenVerifing(token);
//		notesDTO.setUserid(id);
//		Notes note = modelMapper.map(notesDTO, Notes.class);
//		note = notesRepository.save(note);
//		rabbitTemplate.convertAndSend(ApplicationConfiguration.queueName, note);
//	}
//
//	@Override
//	public void updateNote(String token, Notes notes) throws NoteException {
//		TokenVerify.tokenVerifing(token);
//		notes.setLastModifiedStamp(LocalDate.now());
//		Notes updateNote = notesRepository.save(notes);
//		elasticService.update(updateNote);
//	}
//
//	@Override
//	public void deleteNote(String token, long notesId) throws NoteException {
//		TokenVerify.tokenVerifing(token);
//		Notes idnote = notesRepository.findById(notesId).get();
//		idnote.getLabels().forEach(x -> x.getNotes().remove(idnote));
//		notesRepository.delete(idnote);
//		elasticService.delete(notesId);
//	}
//
//	@Override
//	public List<SendingNotes> listAllNotes(String token, String archive, String trash) throws NoteException {
//		long id = TokenVerify.tokenVerifing(token);
//		
//		List<Notes> notesList = notesRepository.findAllById(id, Boolean.valueOf(archive), Boolean.valueOf(trash))
//				.orElse(new ArrayList<Notes>());
//		notesList.addAll(collabservice.getCollabNotes(token));
//
//		List<SendingNotes> xyz = new ArrayList<SendingNotes>();
//
//		for (int i = 0; i < notesList.size(); i++) {
//			List<BigInteger> ll = new ArrayList<BigInteger>();
//			Optional<List<Object>> optionalList = collabRepo.findAllUsersOfNote(notesList.get(i).getId());
//			SendingNotes zz = null;
//			if (optionalList.isPresent()) {
//				optionalList.get().stream().forEach(x -> ll.add((BigInteger) x));
//				ResponseEntity<CollabUserDetails[]> response = restTemplate.postForEntity(ROOT_URI, ll,
//						CollabUserDetails[].class);
//				zz = new SendingNotes(notesList.get(i), Arrays.asList(response.getBody())); // ll at response.getBody()
//			} else {
//				zz = new SendingNotes(notesList.get(i), new ArrayList<CollabUserDetails>());
//
//			}
//			xyz.add(zz);
//		}
//
//		return xyz;
//
//	}
//
//	private void removeNote(Notes notes, Labels l) {
//		l.getNotes().remove(notes);
//		notesRepository.save(notes);
//	}
//
//	@Override
//	public List<Notes> matchedNotes(String token, String searchContent, boolean isArchive, boolean isTrash)
//			throws NoteException {
//		long userid = TokenVerify.tokenVerifing(token);
//		Map<String, Float> fields = new HashMap<String, Float>();
//		fields.put("title", 3.0f);
//		fields.put("content", 2.0f);  
//
//		Map<String, Object> restriction = new HashMap<String, Object>();
//		restriction.put("archive", isArchive);
//		restriction.put("trash", isTrash);
//		restriction.put("userid", userid);
//		List<Notes> list = service.multipleFieldQuery(fields, searchContent, restriction, "notesdata", "notes");
//		return list;
//	}
//
//	@Override
//	public void updateNoteImage(long id, MultipartFile file) {
//		Notes note = notesRepository.findById(id).get();
//		UUID uuid = UUID.randomUUID();
//		String uuidString = uuid.toString();
//		try {
//			Files.copy(file.getInputStream(), this.rootLocation.resolve(uuidString),
//					StandardCopyOption.REPLACE_EXISTING);
//			note.setImage(uuid.toString());
//			notesRepository.save(note);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public Resource getNoteImage(long id) {
//		Notes note = notesRepository.findById(id).get();
//		Path file = rootLocation.resolve(note.getImage());
//		try {
//			Resource resource = new UrlResource(file.toUri());
//			if (resource.exists() || resource.isReadable()) {
//				return resource;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public List<SendingNotes> listLabelNotes(String token,String label)throws NoteException {
//		long labelId=labelRepository.findIdByLabelName(label).get();
//			TokenVerify.tokenVerifing(token);						
//		List<Notes> list=labelRepository.findById(labelId).get().getNotes().stream().collect(Collectors.toList());												
//		List<SendingNotes> xyz = new ArrayList<SendingNotes>();
//		list.stream().forEach( x -> xyz.add(new SendingNotes(x,  new ArrayList<CollabUserDetails>())));
//		return xyz;
//	}
//
//}
