package com.bridgelabz.fundoo.note.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bridgelabz.fundoo.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_note")
public class Note 
{
	@Id
	@GeneratedValue
	private long noteId;	 
	
	private String title;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private User user;
	
	
}
