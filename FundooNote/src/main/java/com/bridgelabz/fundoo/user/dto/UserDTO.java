package com.bridgelabz.fundoo.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO 
{
	
	@Column(name="name")
	@NotEmpty(message = "please provide your name")
	private String name;

	@Column(name="Email", nullable=false, unique=true)
	@Email(message = "Please provide a valid e-mail")
	@NotEmpty(message="Please provide valid email")
	private String email;

	@Column(name="mobileNumber")
	@Pattern(regexp="[0-9]{10}",message = "provide valid mobile number")
	private String mobileNumber;
	
	@Column(name="password")
	private String password;
	
}
