package com.bridgelabz.fundoo.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserInfo {
	private long userId;
	private String email;
	private String name;

}
