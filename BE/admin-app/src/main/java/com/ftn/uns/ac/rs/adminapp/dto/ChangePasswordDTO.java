package com.ftn.uns.ac.rs.adminapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordDTO {

	@NotBlank(message = "Password can not be empty.")
	@Size(min = 7)
	private String oldPassword;

	@NotBlank(message = "Password can not be empty.")
	@Size(min = 7)
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
