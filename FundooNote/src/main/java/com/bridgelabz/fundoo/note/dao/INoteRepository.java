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
}
