package com.ftn.uns.ac.rs.adminapp.dto;

public class RequestDTO {

	private byte[] request;
	private String email;

	public RequestDTO() {
		super();
	}

	public RequestDTO(byte[] request, String email) {
		super();
		this.request = request;
		this.email = email;
	}

	public byte[] getRequest() {
		return request;
	}

	public void setRequest(byte[] request) {
		this.request = request;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
