package com.bridgelabz.fundoo.note.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.bridgelabz.fundoo.user.model.User;

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
	private long id;
	
	@NotEmpty
	private String labelName;

	private LocalDateTime createStamp;
	
	private LocalDateTime updateStamp;
	
	private LocalDateTime deleteStamp;

	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

}
