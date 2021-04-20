package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Hospital;
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
	
	@PostMapping(path = "/requestedCertificate")
	@PreAuthorize("hasAuthority('PRIVILEGE_CSR_DATA')")
	public ResponseEntity<Boolean> isRequested(@RequestBody long id) {
		return new ResponseEntity<>(this.hospitalService.isRequestedCertificate(id), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<ArrayList<Hospital>> findAll() {
		ArrayList<Hospital> hosp = (ArrayList<Hospital>) this.hospitalService.findAll();
		
		if(!hosp.isEmpty())
			return new ResponseEntity<>(hosp, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
