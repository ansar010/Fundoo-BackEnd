package com.bridgelabz.fundoo.note.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bridgelabz.fundoo.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="note_details")
public class Note 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;	 
	
	@Column(name="title")
	private String title;
	
	@Column(name="description",length=2000)
	private String description;
	
	private LocalDateTime createStamp;
	
	private LocalDateTime updateStamp;
	
	private LocalDateTime deleteStamp;
	
	private boolean isArchive;
	private boolean isPin;
	private boolean isTrash;
	
//	@ManyToOne
//	@JoinColumn(name="user_id",referencedColumnName="userId")
//    private User userId;
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	
	// Performing mapping to consist collection of  labels
	@ManyToMany(mappedBy="notes")
	private Set<Label> label;
	
}
