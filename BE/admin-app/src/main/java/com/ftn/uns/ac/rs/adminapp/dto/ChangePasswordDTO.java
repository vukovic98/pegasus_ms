package com.ftn.uns.ac.rs.adminapp.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordDTO {

	@NotBlank
	private String oldPassword;

	@NotBlank
	private String newPassword;

	public ChangePasswordDTO() {
		super();
	}

	public ChangePasswordDTO(@NotBlank String oldPassword, @NotBlank String newPassword) {
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
