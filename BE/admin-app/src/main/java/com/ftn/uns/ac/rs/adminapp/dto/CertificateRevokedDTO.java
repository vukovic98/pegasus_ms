package com.ftn.uns.ac.rs.adminapp.dto;

public class CertificateRevokedDTO {

	private boolean revoked;

	public CertificateRevokedDTO() {
		super();
	}

	public CertificateRevokedDTO(boolean revoked) {
		super();
		this.revoked = revoked;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

}
