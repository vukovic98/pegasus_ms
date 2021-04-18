package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.List;

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
import com.ftn.uns.ac.rs.hospitalapp.beans.Hospital;
import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.dto.AddUserDTO;
import com.ftn.uns.ac.rs.hospitalapp.service.AdminService;
import com.ftn.uns.ac.rs.hospitalapp.service.AuthorityService;
import com.ftn.uns.ac.rs.hospitalapp.service.DoctorService;
import com.ftn.uns.ac.rs.hospitalapp.service.HospitalService;
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
	private HospitalService hospitalService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private DoctorService doctorService;

	@PostMapping()
	public ResponseEntity<HttpStatus> addUser(@RequestBody AddUserDTO dto) {
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

					Hospital h = this.hospitalService.findByName(dto.getHospital());

					if (h != null)
						a.setHospital(h);
					else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}

					a.setAuthorities(this.authService.findByName("ROLE_ADMIN"));

					Admin ok = this.adminService.save(a);

					if (ok != null)
						return new ResponseEntity<>(HttpStatus.OK);
					else {
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

					Hospital h = this.hospitalService.findById(Long.parseLong(dto.getHospital()));

					if (h != null)
						a.setHospital(h);
					else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}

					a.setAuthorities(this.authService.findByName("ROLE_DOCTOR"));

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

	@PostMapping(path = "/change-authority")
	public ResponseEntity<HttpStatus> changeRole(@RequestBody long id) {
		//TODO MOVE FROM ONE TABLE TO ANOTHER
		User u = this.userService.findById(id);

		if (u != null) {

			u.setAuthorities(null);
			this.userService.save(u);

			if (u instanceof Admin) {
				List<Authority> a = this.authService.findByName("ROLE_DOCTOR");

				u.setAuthorities(a);

				this.userService.save(u);

				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				List<Authority> a = this.authService.findByName("ROLE_ADMIN");

				u.setAuthorities(a);

				this.userService.save(u);

				return new ResponseEntity<>(HttpStatus.OK);
			}

		} else {
			System.out.println("ima ");

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
