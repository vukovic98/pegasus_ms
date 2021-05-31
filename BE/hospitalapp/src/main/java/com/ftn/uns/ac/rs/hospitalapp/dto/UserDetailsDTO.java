package com.ftn.uns.ac.rs.hospitalapp.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDetailsDTO implements Serializable {

	private long id;
	
	@NotBlank(message = "Email can not be empty.")
	@Email(message = "Email format is not valid.")
	@Pattern(regexp = "[a-z]+[1-9]*\\.(gsm)?hospital@mailinator.com", message = "Wrong email format. Example: jena123.gsmhospital@mailinator.com")
	private String email;
	
	@NotBlank(message = "First name can not be empty.")
	@Size(max = 25)
	@Pattern(regexp = "[A-Z][a-z]+", message="First name must start with capital letter and can contain only letters.")
	private String firstName;
	
	@NotBlank(message = "Last name can not be empty.")
	@Size(max = 25)
	@Pattern(regexp = "[A-Z][a-z]+", message="Last name must start with capital letter and can contain only letters.")
	private String lastName;
	
	@NotBlank(message = "Hospital can not be empty.")
	private String hospital;
	
	@NotNull
	private boolean enabled;

	public UserDetailsDTO() {
		super();
	}

	public UserDetailsDTO(long id, String email, String firstName, String lastName, String hospital, boolean enabled) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hospital = hospital;
		this.enabled = enabled;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
