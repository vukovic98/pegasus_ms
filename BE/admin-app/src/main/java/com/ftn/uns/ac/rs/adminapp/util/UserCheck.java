package com.ftn.uns.ac.rs.adminapp.util;

import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.adminapp.dto.LoginDTO;

@Component
public class UserCheck {

	public boolean loginCheck(LoginDTO dto) {
		if(dto != null && dto.getEmail() != null && dto.getPassword() != null) {
			if(!dto.getEmail().equalsIgnoreCase("") && !dto.getPassword().equalsIgnoreCase(""))
				return true;
			else
				return false;
		} else
			return false;
	}
	
}
