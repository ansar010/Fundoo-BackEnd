package com.bridgelabz.fundoo.note.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Label;


@Repository
public interface ILabelRepository extends JpaRepository<Label, Long>{

	@Query(value="select * from label_details where user_id= :userId ",nativeQuery=true)
	List<Label> findAllById(long userId);
	
	Optional<Label> findBylabelName(String labelName);
//	@Query(value="select * from label_details where user_id= :userId ",nativeQuery=true)
//	Optional<List<Label>> findAllById(long userId);

}
