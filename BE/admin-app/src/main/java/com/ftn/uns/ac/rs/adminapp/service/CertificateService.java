package com.ftn.uns.ac.rs.adminapp.service;

import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.adminapp.repository.CertificateRepository;

@Service
public class CertificateService {

	@Autowired
	private CertificateRepository certRepository;
	
	public ArrayList<X509Certificate> findAllCertificates() {
		return this.certRepository.findAllCertificates();
	}
}
