package com.ftn.uns.ac.rs.hospitalapp.dto;

import java.io.Serializable;

public class UserDetailsDTO implements Serializable {

	private long id;
	private String email;
	private String firstName;
	private String lastName;
	private String hospital;
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
