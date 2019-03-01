package com.bridgelabz.fundoo.note.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;

@Repository
public interface INoteRepository extends JpaRepository<Note, Long> {
//	List<Note> findByfindAllByUser(long userId);
}
