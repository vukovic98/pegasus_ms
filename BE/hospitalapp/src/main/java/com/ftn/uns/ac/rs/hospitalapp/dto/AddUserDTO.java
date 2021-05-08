package com.ftn.uns.ac.rs.hospitalapp.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddUserDTO {

	@NotBlank(message = "Email can not be empty.")
	@Email(message = "Email format is not valid.")
	private String email;
	
	@NotBlank(message = "Password can not be empty.")
	private String password;
	
	@NotBlank(message = "First name can not be empty.")
	private String firstName;
	
	@NotBlank(message = "Last name can not be empty.")
	private String lastName;
	
	@NotNull(message = "Role can not be empty.")
	private int role;
	
	@NotBlank(message = "Hospital can not be empty.")
	private String hospital;

	public AddUserDTO() {
		super();
	}

	public AddUserDTO(String email, String password, String firstName, String lastName, int role, String hospital) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.hospital = hospital;
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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

}
