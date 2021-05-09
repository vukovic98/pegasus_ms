package com.ftn.uns.ac.rs.adminapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDTO {

	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;

	public LoginDTO() {
		super();
	}

	public LoginDTO(@NotBlank @Email String email, @NotBlank String password) {
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
