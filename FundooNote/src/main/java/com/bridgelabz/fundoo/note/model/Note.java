package com.bridgelabz.fundoo.note.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="noteId")
	private long noteId;	 
	
	@Column(name="title")
	private String title;
	
	@Column(name="description",length=2000)
	private String description;
	
	
	private LocalDate createStamp;
	
	private LocalDate updateStamp;
	
	private LocalDate deleteStamp;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
}
