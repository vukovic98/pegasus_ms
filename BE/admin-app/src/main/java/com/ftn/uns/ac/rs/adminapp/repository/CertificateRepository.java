package com.ftn.uns.ac.rs.adminapp.repository;

import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.adminapp.util.CertificateUtil;

@Repository
public class CertificateRepository {

	@Autowired
	private Environment env;

	public ArrayList<X509Certificate> findAllCertificates() {
		ArrayList<X509Certificate> certs = new ArrayList();

		certs = CertificateUtil.getCertificateDetails(env.getProperty("jks.store"), env.getProperty("jks.password"));

		return certs;
	}

}
