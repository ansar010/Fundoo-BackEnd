package com.bridgelabz.fundoo.note.model;

import java.sql.Timestamp;
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
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="note_details")
@ToString
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
	
	
	private Timestamp remainder;
	
	
	@JsonProperty
	@Column(columnDefinition="tinyint(1) default 0 not null")
	private boolean isArchive;
	
	@JsonProperty
	@Column(columnDefinition="tinyint(1) default 0 not null")
	private boolean isPin;
	
	@JsonProperty
	@Column(columnDefinition="tinyint(1) default 0 not null")
	private boolean isTrash;
	
	private String color;
	
	@Column(columnDefinition="varchar(500)")
	private String image;
	
	
//	@ManyToOne
//	@JoinColumn(name="user_id",referencedColumnName="userId")
//    private User userId;
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	
	// Performing mapping to consist collection of  labels
	@ManyToMany(mappedBy="notes")
	private Set<Label> labels;
	
}
