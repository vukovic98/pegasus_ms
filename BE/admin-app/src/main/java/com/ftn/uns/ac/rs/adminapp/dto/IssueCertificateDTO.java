package com.ftn.uns.ac.rs.adminapp.dto;

public class IssueCertificateDTO {

	private long id;
	private String template;

	public IssueCertificateDTO() {
		super();
	}

	public IssueCertificateDTO(long id, String extensions) {
		super();
		this.id = id;
		this.template = extensions;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String extensions) {
		this.template = extensions;
	}

}
