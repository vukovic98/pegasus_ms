package com.ftn.uns.ac.rs.hospitalapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDTO {

	@NotBlank(message = "Email can not be empty.")
	@Email(message = "Email format is not valid.")
	@Pattern(regexp = "[a-z]+[1-9]*\\.(gsm)?hospital@mailinator.com", message = "Wrong email format. Example: jena123.gsmhospital@mailinator.com")
	private String email;
	
	@NotBlank(message = "Password can not be empty.")
	@Size(min=7)
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
