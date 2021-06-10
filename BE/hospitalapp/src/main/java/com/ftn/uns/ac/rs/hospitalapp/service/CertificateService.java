package com.ftn.uns.ac.rs.hospitalapp.service;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.CertificateRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.CertificateRevokedException;

@Service
public class CertificateService {

	@Autowired
	private CertificateRepository certRepository;

	public PublicKey getBobsPublicKey() throws CertificateRevokedException {
		return this.certRepository.getBobsPublicKey();
	}

	public PrivateKey getMyPrivateKey() {
		return this.certRepository.getMyPrivateKey();
	}
	
	public PublicKey getLogSimPublicKey() throws CertificateRevokedException {
		return this.certRepository.getLogSimPublicKey();
	}
	
	public PublicKey getBloodDevicePublicKey() throws CertificateRevokedException {
		return this.certRepository.getBloodDevicePublicKey();
	}

	public PublicKey getHeartMonitorPublicKey() throws CertificateRevokedException {
		return this.certRepository.getHeartMonitorPublicKey();
	}
	public PublicKey getNeurologicalDevicePublicKey() throws CertificateRevokedException {
		return this.certRepository.getNeurologicalDevicePublicKey();
	}
	public PublicKey getTemperatureDevicePublicKey() throws CertificateRevokedException {
		return this.certRepository.getTemperatureDevicePublicKey();
	}

}
