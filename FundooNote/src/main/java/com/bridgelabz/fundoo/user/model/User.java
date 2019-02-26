package com.bridgelabz.fundoo.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.bridgelabz.fundoo.note.model.Note;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="user_details")
@ToString
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userId;

	@Column(name="verification")
	boolean isVarified;

	@Column(name="name")
	@NotEmpty(message = "please provide your name")
	private String name;

	@Column(name="email", nullable=false)
	@Email(message = "Please provide a valid e-mail")
	@NotEmpty(message="Please provide valid email")
	private String email;

	@Column(name="register_Stamp")
	private LocalDateTime account_registered;

	@Column(name="ac_update_Stamp")
	private LocalDateTime account_update;


	@Column(name="mobileNumber")
	@Pattern(regexp="[0-9]{10}",message = "provide valid mobile number")
	private String mobileNumber;

	@NotEmpty(message="Please provide password")
	@Column(name="password")
	private String password;

	//O to many using Unidirectional
	@OneToMany(targetEntity=Note.class,cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",referencedColumnName="userId")
	private Set<Note> notes;//To build O to Many relation
}
