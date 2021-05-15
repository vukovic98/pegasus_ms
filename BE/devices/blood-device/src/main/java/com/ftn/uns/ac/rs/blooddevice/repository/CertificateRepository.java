package com.ftn.uns.ac.rs.blooddevice.repository;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.blooddevice.util.CertificateUtil;

@Repository
public class CertificateRepository {

	@Autowired
	private Environment env;
	
	public PrivateKey getMyPrivateKey() {
		return CertificateUtil.getMyPrivateKey(env.getProperty("server.ssl.key-store"), env.getProperty("server.ssl.key-store-password"));
	}
	
	public PublicKey getBobsPublicKey() {
		return CertificateUtil.getBobsPublicKey(env.getProperty("bob.store"));
	}

}
