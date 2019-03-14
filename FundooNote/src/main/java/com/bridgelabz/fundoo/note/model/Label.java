package com.bridgelabz.fundoo.note.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.bridgelabz.fundoo.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Label_Details")
public class Label 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	
	@NotEmpty
	private String labelName;

	private LocalDateTime createStamp;
	
	private LocalDateTime updateStamp;
	
	private LocalDateTime deleteStamp;

	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	// Performing mapping to consist collection notes
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="Note_Label",
			joinColumns=@JoinColumn(name="label_id",referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="note_id",referencedColumnName="ID"))
	@JsonIgnore
	private Set<Note> notes;
}
