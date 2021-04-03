package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.service.UserService;

@RestController
@RequestMapping(path = "/certificate")
public class CertificateController {

	@Autowired
	private UserService userService;

//	@PostMapping(path = "/addCertificate")
//	public ResponseEntity<HttpStatus> addCertificate() {
//		
//	}

	@GetMapping(path = "/request")
	public ResponseEntity<byte[]> generateCertificate() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();

		User u = this.userService.findByEmail(email);

		KeyPairGenerator gen = null;
		try {
			gen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		gen.initialize(2048);
		KeyPair pair = gen.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();

		String data = new String();

		data = "C=RS, ST=Serbia, L=Serbia, O=Pegasus MS, OU=" + u.getHospital().getName() + ", CN=" + u.getFirstName() + " " + u.getLastName()
				+ ", EMAILADDRESS=" + email + "";
		X500Principal subject = new X500Principal(data);

		ContentSigner signGen = null;
		try {
			signGen = new JcaContentSignerBuilder("SHA1withRSA").build(privateKey);
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		}

		PKCS10CertificationRequestBuilder builder = new JcaPKCS10CertificationRequestBuilder(subject, publicKey);
		PKCS10CertificationRequest csr = builder.build(signGen);

		try {
			return new ResponseEntity<byte[]>(csr.getEncoded(), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
