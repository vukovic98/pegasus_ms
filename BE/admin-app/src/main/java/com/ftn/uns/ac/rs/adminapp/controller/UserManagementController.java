package com.ftn.uns.ac.rs.adminapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.dto.AddUserDTO;
import com.ftn.uns.ac.rs.adminapp.dto.ChangePasswordDTO;
import com.ftn.uns.ac.rs.adminapp.service.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserManagementController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate;

	@PostMapping(path = "/change-password")
	@PreAuthorize("hasAuthority('PRIVILEGE_CHANGE_PASSWORD')")
	public ResponseEntity<HttpStatus> changePassword(@RequestBody @Valid ChangePasswordDTO dto) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = this.userService.findByEmail(username);

		if (user != null) {
			BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

			if (enc.matches(dto.getOldPassword(), user.getPassword())) {
				user.setPassword(enc.encode(dto.getNewPassword()));

				this.userService.save(user);

				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/delete")
	@PreAuthorize("hasAuthority('PRIVILEGE_MANAGE_USERS')")
	public ResponseEntity<HttpStatus> deleteUser(@RequestBody long id) {

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
	public ResponseEntity<HttpStatus> addUser(@RequestBody @Valid AddUserDTO dto) {

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
