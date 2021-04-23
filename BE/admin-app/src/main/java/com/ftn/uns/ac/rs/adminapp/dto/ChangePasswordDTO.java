package com.ftn.uns.ac.rs.adminapp.dto;

public class ChangePasswordDTO {

	private String oldPassword;
	private String newPassword;

	public ChangePasswordDTO() {
		super();
	}

	public ChangePasswordDTO(String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
