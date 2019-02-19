package com.bridgelabz.fundoo.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
	@Column(name="id")
//	@NotEmpty(message = "please provide your id")
	private long id;
	
	@Column(name="verification")
	boolean isVarified;
	
	@Column(name="name")
	@NotEmpty(message = "please provide your name")
	private String name;

	@Column(name="email", nullable=false)
	@Email(message = "Please provide a valid e-mail")
	@NotEmpty(message="Please provide valid email")
	private String email;

	@Column(name="mobileNumber")
	@Pattern(regexp="[0-9]{10}",message = "provide valid mobile number")
	private String mobileNumber;
	
	@Column(name="password")
	private String password;
	
	@Column(name="confirmPassword")
	private String confirmPassword;

		
}
