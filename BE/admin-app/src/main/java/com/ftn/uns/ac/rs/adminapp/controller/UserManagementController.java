package com.ftn.uns.ac.rs.adminapp.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.adminapp.dto.AddUserDTO;

@RestController
@RequestMapping(path = "/users")
public class UserManagementController {

	@PostMapping(path = "/delete")
	@PreAuthorize("hasAuthority('PRIVILEGE_MANAGE_USERS')")
	public ResponseEntity<HttpStatus> deleteUser(@RequestBody long id) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<Long> request = new HttpEntity<Long>(id);
		ResponseEntity<HttpStatus> responseEntityStr = null;

		try {
			responseEntityStr = restTemplate.postForEntity("https://localhost:8081/users/delete", request,
					HttpStatus.class);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(HttpStatus.valueOf(e.getRawStatusCode()));
		}

		return new ResponseEntity<>(responseEntityStr.getStatusCode());

	}

	@PostMapping()
	@PreAuthorize("hasAuthority('PRIVILEGE_MANAGE_USERS')")
	public ResponseEntity<HttpStatus> addUser(@RequestBody AddUserDTO dto) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<AddUserDTO> request = new HttpEntity<AddUserDTO>(dto);
		ResponseEntity<HttpStatus> responseEntityStr = null;

		try {
			responseEntityStr = restTemplate.postForEntity("https://localhost:8081/users", request, HttpStatus.class);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(HttpStatus.valueOf(e.getRawStatusCode()));
		}

		return new ResponseEntity<>(responseEntityStr.getStatusCode());
	}

	@PostMapping(path = "/change-authority")
	@PreAuthorize("hasAuthority('PRIVILEGE_MANAGE_USERS')")
	public ResponseEntity<HttpStatus> changeRole(@RequestBody long id) {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<Long> request = new HttpEntity<Long>(Long.valueOf(id));
		ResponseEntity<HttpStatus> responseEntityStr = null;

		try {
			responseEntityStr = restTemplate.postForEntity("https://localhost:8081/users/change-authority", request,
					HttpStatus.class);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(HttpStatus.valueOf(e.getRawStatusCode()));
		}

		return new ResponseEntity<>(responseEntityStr.getStatusCode());
	}

}
