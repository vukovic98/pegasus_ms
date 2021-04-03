package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.hospitalapp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	public User login(LoginDTO dto) {
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		 
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(dto.getEmail());
		
		if(matcher.matches()) {
			User found = this.userRepository.findByEmail(dto.getEmail());
			
			if(found != null) {
				BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
				
				if (enc.matches(dto.getPassword(), found.getPassword()))
					return found;
				else
					return null;
			}
			else
				return null;
		} else {
			return null;
		}
	}
	
}
