package com.ftn.uns.ac.rs.hospitalapp.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hospital")
@Embeddable
public class Hospital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hospital_id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "certified", nullable = false)
	private boolean certified = false;

	@Column(name = "req_cert", nullable = false)
	private boolean requestedCertificate = false;

	public Hospital() {
		super();
	}

	public Hospital(Long id, String name, boolean certified, boolean req) {
		super();
		this.id = id;
		this.name = name;
		this.certified = certified;
		this.requestedCertificate = req;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isRequestedCertificate() {
		return requestedCertificate;
	}

	public void setRequestedCertificate(boolean requestedCertificate) {
		this.requestedCertificate = requestedCertificate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCertified() {
		return certified;
	}

	public void setCertified(boolean certified) {
		this.certified = certified;
	}

}
