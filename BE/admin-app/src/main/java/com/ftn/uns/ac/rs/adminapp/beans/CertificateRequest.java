package com.ftn.uns.ac.rs.adminapp.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Embeddable
@Table(name = "certificate_request")
public class CertificateRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	@Column(name = "certRequest")
	private byte[] certificateRequest;

	public CertificateRequest() {
		super();
	}

	public CertificateRequest(byte[] certificateRequest) {
		super();
		this.certificateRequest = certificateRequest;
	}

	public byte[] getCertificateRequest() {
		return certificateRequest;
	}

	public void setCertificateRequest(byte[] certificateRequest) {
		this.certificateRequest = certificateRequest;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
