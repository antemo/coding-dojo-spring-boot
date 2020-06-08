package com.assignment.spring.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRegistrationDto {

	@NotBlank
	@Size(max = 50)
	private String userName;

	@NotBlank
	@Size(max = 100)
	private String fullName;

	@NotBlank
	@Size(max = 100)
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
