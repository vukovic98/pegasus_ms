package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Admin;
import com.ftn.uns.ac.rs.hospitalapp.beans.Authority;
import com.ftn.uns.ac.rs.hospitalapp.beans.Doctor;
import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.dto.AddUserDTO;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.AdminService;
import com.ftn.uns.ac.rs.hospitalapp.service.AuthorityService;
import com.ftn.uns.ac.rs.hospitalapp.service.DoctorService;
import com.ftn.uns.ac.rs.hospitalapp.service.UserService;
import com.ftn.uns.ac.rs.hospitalapp.util.UserCheck;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authService;

	@Autowired
	private UserCheck userCheck;

	@Autowired
	private AdminService adminService;

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private LoggerProxy logger;

	@PostMapping(path = "/delete")
	public ResponseEntity<HttpStatus> deleteUser(@RequestBody long id) {
		if (id >= 0) {
			User u = this.userService.findById(id);

			if (u != null) {

				if (u instanceof Admin) {
					ArrayList<Admin> admins = (ArrayList<Admin>) this.adminService.findAll();

					if (admins.size() <= 1)
						return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
				}

				u.setAuthorities(null);

				this.userService.save(u);

				boolean ok = this.userService.deleteUserById(id);

				if (ok) {
					this.logger.info("Successfull attempt for deleting user [ " + id + " ]", UserController.class);
					
					return new ResponseEntity<>(HttpStatus.OK);
				}
				else {
					this.logger.error("[ HOSPITAL APP ERROR STATUS - ] Failed attempt for deleting user [ " + id + " ]", UserController.class);
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

			} else {
				this.logger.error("[ HOSPITAL APP ERROR STATUS - ] Failed attempt for deleting user [ " + id + " ]", UserController.class);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			this.logger.error("[ HOSPITAL APP ERROR STATUS - ] Failed attempt for deleting user [ " + id + " ]", UserController.class);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping()
	public ResponseEntity<HttpStatus> addUser(@Valid @RequestBody AddUserDTO dto) {
		User u = this.userService.findByEmail(dto.getEmail());

		if (u == null) {
			if (this.userCheck.addUserCheck(dto)) {
				if (dto.getRole() == 1) {
//					ADMIN

					Admin a = new Admin();

					a.setEmail(dto.getEmail());
					a.setEnabled(true);
					a.setFirstName(dto.getFirstName());
					a.setLastName(dto.getLastName());

					BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
					a.setPassword(enc.encode(dto.getPassword()));

					a.setHospital(dto.getHospital());

					a.setAuthorities(this.authService.findByName("ROLE_ADMIN"));
					a.setLastActivityTime(LocalDate.now());
					Admin ok = this.adminService.save(a);

					if (ok != null)
						return new ResponseEntity<>(HttpStatus.OK);
					else {
						System.out.println("ne savuca");
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}

				} else {
//					DOCTOR

					Doctor a = new Doctor();

					a.setEmail(dto.getEmail());
					a.setEnabled(true);
					a.setFirstName(dto.getFirstName());
					a.setLastName(dto.getLastName());

					BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
					a.setPassword(enc.encode(dto.getPassword()));

					a.setHospital(dto.getHospital());

					a.setAuthorities(this.authService.findByName("ROLE_DOCTOR"));
					a.setLastActivityTime(LocalDate.now());
					Doctor ok = this.doctorService.save(a);

					if (ok != null)
						return new ResponseEntity<>(HttpStatus.OK);
					else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Transactional
	@PostMapping(path = "/change-authority")
	public ResponseEntity<HttpStatus> changeRole(@RequestBody long id) {

		User u = this.userService.findById(id);

		if (u != null) {
			
			if(u instanceof Admin) {
				ArrayList<Admin> admins = (ArrayList<Admin>) this.adminService.findAll();

				if (admins.size() <= 1)
					return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}

			u.setAuthorities(null);
			this.userService.save(u);

			boolean ok = this.userService.deleteUserById(u.getId());

			if (ok) {
				if (u instanceof Admin) {

					List<Authority> a = this.authService.findByName("ROLE_DOCTOR");

					Doctor d = new Doctor();

					d.setAuthorities(a);
					d.setEmail(u.getEmail());
					d.setEnabled(u.isEnabled());
					d.setFirstName(u.getFirstName());
					d.setHospital(u.getHospital());
					d.setLastName(u.getLastName());
					d.setPassword(u.getPassword());

					this.doctorService.save(d);

					return new ResponseEntity<>(HttpStatus.OK);
				} else {
					List<Authority> a = this.authService.findByName("ROLE_ADMIN");

					Admin d = new Admin();

					d.setAuthorities(a);
					d.setEmail(u.getEmail());
					d.setEnabled(u.isEnabled());
					d.setFirstName(u.getFirstName());
					d.setHospital(u.getHospital());
					d.setLastName(u.getLastName());
					d.setPassword(u.getPassword());

					this.adminService.save(d);

					return new ResponseEntity<>(HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
