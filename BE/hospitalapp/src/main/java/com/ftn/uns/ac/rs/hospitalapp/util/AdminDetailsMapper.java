package com.ftn.uns.ac.rs.hospitalapp.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.hospitalapp.beans.Admin;
import com.ftn.uns.ac.rs.hospitalapp.dto.UserDetailsDTO;

@Component
public class AdminDetailsMapper {

	public UserDetailsDTO userToDto(Admin u) {
		UserDetailsDTO dto = new UserDetailsDTO();
		
		dto.setEmail(u.getEmail());
		dto.setEnabled(u.isEnabled());
		dto.setFirstName(u.getFirstName());
		dto.setHospital(u.getHospital());
		dto.setId(u.getId());
		dto.setLastName(u.getLastName());
		
		return dto;
	}
	
	public ArrayList<UserDetailsDTO> listToDTO(List<Admin> users) {
		ArrayList<UserDetailsDTO> dtos = new ArrayList<>();
		
		for(Admin u : users) {
			dtos.add(this.userToDto(u));
		}
		
		return dtos;
	}
	
}
