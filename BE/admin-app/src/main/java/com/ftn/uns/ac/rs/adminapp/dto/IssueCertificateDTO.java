package com.ftn.uns.ac.rs.adminapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IssueCertificateDTO {

	@NotNull
	private long id;

	@NotBlank
	private String template;

	public IssueCertificateDTO() {
		super();
	}

	public IssueCertificateDTO(@NotNull long id, @NotBlank String template) {
		super();
		this.id = id;
		this.template = template;
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
