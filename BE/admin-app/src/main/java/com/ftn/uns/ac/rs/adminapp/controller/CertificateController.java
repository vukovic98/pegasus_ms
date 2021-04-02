package com.ftn.uns.ac.rs.adminapp.controller;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.adminapp.dto.X509DetailsDTO;
import com.ftn.uns.ac.rs.adminapp.service.CertificateService;

@RestController()
@RequestMapping(path = "/certificate")
public class CertificateController {

	@Autowired
	private CertificateService certService;
	
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping()
	public ResponseEntity<ArrayList<X509DetailsDTO>> findAll() {
		ArrayList<X509DetailsDTO> listDto = new ArrayList<>();
		ArrayList<X509Certificate> certs = this.certService.findAllCertificates();
		
		for(X509Certificate x : certs) {
			X509DetailsDTO dto = new X509DetailsDTO();
			
			dto.setSerialNum(x.getSerialNumber().toString());
			dto.setIssuedDate(sdf.format(x.getNotBefore()));
			dto.setIssuer(x.getIssuerX500Principal().getName());
			dto.setSubject(x.getSubjectX500Principal().getName());
			dto.setValidToDate(sdf.format(x.getNotAfter()));
			
			listDto.add(dto);
		}
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}
	
}
