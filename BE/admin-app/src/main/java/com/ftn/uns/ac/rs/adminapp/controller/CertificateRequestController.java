package com.ftn.uns.ac.rs.adminapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;
import com.ftn.uns.ac.rs.adminapp.dto.CertificateRequestDTO;
import com.ftn.uns.ac.rs.adminapp.service.CertificateRequestService;

@RestController()
@RequestMapping(path = "/certificate-request")
public class CertificateRequestController {

	@Autowired
	private CertificateRequestService certificateReqService;
	
	@GetMapping()
	public ResponseEntity<ArrayList<CertificateRequestDTO>> getAll(){
		ArrayList<CertificateRequestDTO> dtos =  this.certificateReqService.findAll();
		return new ResponseEntity<>(dtos,HttpStatus.OK);
	
	}
	
	@PostMapping(path = "/denyRequest")
	public ResponseEntity<HttpStatus> denyRequest(@RequestBody long id) {
		CertificateRequest c = this.certificateReqService.findOneById(id);
		
		if(c != null) {
			boolean ok = this.certificateReqService.remove(c);
			if(ok)
				return new ResponseEntity<>(HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public ResponseEntity<Boolean> save(@RequestBody byte[] request){
		boolean ok = this.certificateReqService.save(request);
		if (ok)
			return new ResponseEntity<>(ok,HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
