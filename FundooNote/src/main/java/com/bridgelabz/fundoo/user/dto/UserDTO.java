package com.bridgelabz.fundoo.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO 
{
	
	@NotEmpty(message = "please provide your name")
	private String name;

	@Email(message = "Please provide a valid e-mail")
	@NotEmpty(message="Please provide valid email")
	private String email;

	@Pattern(regexp="[0-9]{10}",message = "provide valid mobile number")
	private String mobileNumber;
	
	@Length(min=6 , max = 10, message = "password must min 6 and max 10") 
	private String password;
	
}
