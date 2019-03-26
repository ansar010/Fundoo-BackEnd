package com.bridgelabz.fundoo.note.dao;


import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;


@Repository
public interface INoteRepository extends JpaRepository<Note, Long> {
	//	List<Note> findByfindAllByUser(long userId);

	@Query(value="select * from note_details where user_id=:userId AND is_archive=:archive AND is_trash=:trash",nativeQuery=true)//here native query true indicate :string=true
	Optional<List<Note>> findAllById(@Param("userId") long userId, @Param("archive")boolean is_Archive,@Param("trash")boolean is_Trash);//@param(string)

	
	Optional<List<Note>> findAllById(@Param("noteId") long noteId);//@param(string)

//	@Query(value="select * from notes where userid= :userid AND is_archive=:archive AND is_trash =:trash",nativeQuery=true) // :str = true
//	Optional<List<Notes>> findAllById(@Param("userid")long userid,@Param("archive") boolean archive,@Param("trash") boolean trash); //@Param("str") String str
//	
//	@Query(value="select * from notes where id IN (:ids)",nativeQuery=true) 
//Optional<List<Notes>> findAllCollabNotes(@Param("ids") List<Long> allNotesId);

}
