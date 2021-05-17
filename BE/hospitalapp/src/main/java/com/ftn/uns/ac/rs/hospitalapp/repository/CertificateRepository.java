package com.ftn.uns.ac.rs.hospitalapp.repository;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.util.CertificateUtil;

@Repository
public class CertificateRepository {

	@Autowired
	private Environment env;
	
	public PrivateKey getMyPrivateKey() {
		return CertificateUtil.getMyPrivateKey(env.getProperty("trust.store"), env.getProperty("trust.store.password"));
	}
	
	public PublicKey getBobsPublicKey() {
		return CertificateUtil.getBobsPublicKey(env.getProperty("bob.store"));
	}
	
	public PublicKey getBloodDevicePublicKey() {
		return CertificateUtil.getBobsPublicKey(env.getProperty("blood.cert"));
	}
	
	public PublicKey getHeartMonitorPublicKey() {
		return CertificateUtil.getBobsPublicKey(env.getProperty("heart.cert"));
	}

	public PublicKey getNeurologicalDevicePublicKey() {
		return CertificateUtil.getBobsPublicKey(env.getProperty("neuro.cert"));
	}
	
}
