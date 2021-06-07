package com.ftn.uns.ac.rs.hospitalapp.service;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.repository.CertificateRepository;

@Service
public class CertificateService {

	@Autowired
	private CertificateRepository certRepository;

	public PublicKey getBobsPublicKey() {
		return this.certRepository.getBobsPublicKey();
	}

	public PrivateKey getMyPrivateKey() {
		return this.certRepository.getMyPrivateKey();
	}
	
	public PublicKey getLogSimPublicKey() {
		return this.certRepository.getLogSimPublicKey();
	}
	
	public PublicKey getBloodDevicePublicKey() {
		return this.certRepository.getBloodDevicePublicKey();
	}

	public PublicKey getHeartMonitorPublicKey() {
		return this.certRepository.getHeartMonitorPublicKey();
	}
	public PublicKey getNeurologicalDevicePublicKey() {
		return this.certRepository.getNeurologicalDevicePublicKey();
	}
	public PublicKey getTemperatureDevicePublicKey() {
		return this.certRepository.getTemperatureDevicePublicKey();
	}

}
