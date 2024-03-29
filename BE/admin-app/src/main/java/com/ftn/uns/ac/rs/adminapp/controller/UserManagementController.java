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
import com.ftn.uns.ac.rs.adminapp.util.LoggerProxy;

@RestController
@RequestMapping(path = "/users")
public class UserManagementController {

	@Autowired
	private UserService userService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LoggerProxy logger;

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

				this.logger.info("Successfull attempt for changing password for logged user",
						UserManagementController.class);

				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				this.logger.warn("[ WRONG OLD PASSWORD ] Failed attempt for changing password for logged user",
						UserManagementController.class);

				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} else {

			this.logger.error("[ USER NOT FOUND ] Failed attempt for changing password for logged user",
					UserManagementController.class);

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

			this.logger.error("[ HOSPITAL APP ERROR STATUS - " + e.getRawStatusCode()
					+ " ] Failed attempt for deleting user [ " + id + " ]", UserManagementController.class);

			return new ResponseEntity<>(HttpStatus.valueOf(e.getRawStatusCode()));
		}

		this.logger.info("Successfull attempt for deleting user [ " + id + " ]", UserManagementController.class);

		return new ResponseEntity<>(responseEntityStr.getStatusCode());

	}

	@PostMapping()
	@PreAuthorize("hasAuthority('PRIVILEGE_MANAGE_USERS')")
	public ResponseEntity<HttpStatus> addUser(@RequestBody @Valid AddUserDTO dto) {

		System.out.println(dto.getFirstName());
		
		HttpEntity<AddUserDTO> request = new HttpEntity<AddUserDTO>(dto);
		ResponseEntity<HttpStatus> responseEntityStr = null;

		try {
			responseEntityStr = restTemplate.postForEntity("https://localhost:8081/users", request, HttpStatus.class);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();

			this.logger.error("[ HOSPITAL APP ERROR STATUS - " + e.getRawStatusCode()
					+ " ] Failed attempt for adding user [ " + dto.getEmail() + " ]", UserManagementController.class);

			return new ResponseEntity<>(HttpStatus.valueOf(e.getRawStatusCode()));
		}

		this.logger.info("Successfull attempt for adding user [ " + dto.getEmail() + " ]",
				UserManagementController.class);

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

			this.logger.error(
					"[ HOSPITAL APP ERROR STATUS - " + e.getRawStatusCode()
							+ " ] Failed attempt for changing role for user [ " + id + " ]",
					UserManagementController.class);

			return new ResponseEntity<>(HttpStatus.valueOf(e.getRawStatusCode()));
		}

		this.logger.info("Successfull attempt for changing role for user [ " + id + " ]",
				UserManagementController.class);

		return new ResponseEntity<>(responseEntityStr.getStatusCode());
	}

}
