package com.ftn.uns.ac.rs.hospitalapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDataDTO {

	@NotBlank(message = "Email can not be empty.")
	@Email(message = "Email format is not valid.")
	@Pattern(regexp = "[a-z]+[1-9]*\\.(gsm)?hospital@mailinator.com", message = "Wrong email format. Example: jena123.gsmhospital@mailinator.com")
	private String email;
	
	@NotBlank(message = "First name can not be empty.")
	@Size(max = 25)
	@Pattern(regexp = "[A-Z][a-z]+", message="First name must start with capital letter and can contain only letters.")
	private String first_name;
	
	@NotBlank(message = "Last name can not be empty.")
	@Size(max = 25)
	@Pattern(regexp = "[A-Z][a-z]+", message="Last name must start with capital letter and can contain only letters.")
	private String last_name;

	public UserDataDTO() {
		super();
	}

	public UserDataDTO(String email, String first_name, String last_name) {
		super();
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

}
