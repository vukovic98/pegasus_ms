package com.ftn.uns.ac.rs.adminapp.util;

public class RevokeEntry {

	private boolean revoked;
	private int revokeReason;

	public RevokeEntry() {
		super();
	}

	public RevokeEntry(boolean revoked, int revokeReason) {
		super();
		this.revoked = revoked;
		this.revokeReason = revokeReason;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public int getRevokeReason() {
		return revokeReason;
	}

	public void setRevokeReason(int revokeReason) {
		this.revokeReason = revokeReason;
	}

	@Override
	public String toString() {

		String revokeReason = "";

		switch (this.revokeReason) {
		case 1:
			revokeReason = "The private key linked to this certificate has been compromised.";
			break;
		case 2:
			revokeReason = "The certification authority linked to this certificate has been compromised.";
			break;
		case 3:
			revokeReason = "The information on this certificate has changed.";
			break;
		case 4:
			revokeReason = "The user has been issued a replacement certificate.";
			break;
		case 5:
			revokeReason = "The entity using this certificate is no longer operational.";
			break;
		case 6:
			revokeReason = "The certificate holder has requested the revoking.";
			break;
		case 7:
			revokeReason = "The certificate has been temporarily revoked.";
			break;
		default:
			break;
		}
		return revokeReason;
	}

}
