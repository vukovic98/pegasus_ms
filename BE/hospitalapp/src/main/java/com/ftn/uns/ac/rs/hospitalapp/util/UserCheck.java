package com.ftn.uns.ac.rs.hospitalapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.hospitalapp.dto.AddUserDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.LoginDTO;

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
	
	public boolean addUserCheck(AddUserDTO dto) {
		if(dto != null && dto.getEmail() != null && dto.getFirstName() != null && dto.getHospital() != null
				&& dto.getLastName() != null && dto.getPassword() != null && (dto.getRole() == 1 || dto.getRole() == 2)) {
			if(dto.getEmail() != "" && dto.getFirstName() != "" && dto.getHospital() != "" && dto.getLastName() != ""
					&& dto.getPassword() != "") {
				String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

				Pattern pattern = Pattern.compile(regex);

				Matcher matcher = pattern.matcher(dto.getEmail());
				
				if(matcher.matches())
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}
	
}
