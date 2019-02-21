package com.bridgelabz.fundoo.note.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bridgelabz.fundoo.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Entity(name="UserNote")
@Getter
@Setter
public class Note 
{
	@Id
	@GeneratedValue
	private long id;	 
	
	private String title;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
	
	
}
