package com.ftn.uns.ac.rs.hospitalapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDTO {

	@Email(message = "Email format is not valid.")
	@NotBlank(message = "Email can not be empty.")
	private String email;
	
	@NotBlank(message = "Password can not be empty.")
	private String password;

	public LoginDTO() {
		super();
	}

	public LoginDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
