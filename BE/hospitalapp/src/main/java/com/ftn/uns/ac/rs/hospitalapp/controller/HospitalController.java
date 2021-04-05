package com.ftn.uns.ac.rs.hospitalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.service.HospitalService;

@RestController
@RequestMapping(path = "/hospital")
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;
	
	@PostMapping(path = "/certified")
	public ResponseEntity<Boolean> isVerified(@RequestBody long id) {
		return new ResponseEntity<>(this.hospitalService.isCertified(id), HttpStatus.OK);
	}
	
}
