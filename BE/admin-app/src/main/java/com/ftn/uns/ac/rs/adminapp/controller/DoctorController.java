package com.ftn.uns.ac.rs.adminapp.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.adminapp.dto.UserDetailsDTO;
import com.ftn.uns.ac.rs.adminapp.util.PageImplementation;

@RestController
@RequestMapping(path = "/doctor")
public class DoctorController {

	@GetMapping(path = "/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_USERS')")
	public ResponseEntity<PageImplementation<UserDetailsDTO>> findAll(@PathVariable int pageNum) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<PageImplementation<UserDetailsDTO>> responseEntity = 
				restTemplate.exchange("https://localhost:8081/doctor/by-page/" + pageNum, HttpMethod.GET, null,
						new ParameterizedTypeReference<PageImplementation<UserDetailsDTO>>() {
						});

		return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
	}
	
}
