package com.online.market.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UsersPropertiesDto {

	private String username;
	
	private String password;

	private String firstName;

	private String lastName;

	private String email;

	private String mobileNumber;

	private String gender;

	private Date birthdate;

	private String birthplace;
}
