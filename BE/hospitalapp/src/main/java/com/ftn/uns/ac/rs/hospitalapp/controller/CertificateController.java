package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.dto.CertificateDistributionDetailsDTO;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.service.HospitalService;
import com.ftn.uns.ac.rs.hospitalapp.service.UserService;
import com.ftn.uns.ac.rs.hospitalapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.FinalMessage;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/certificate")
public class CertificateController {

	@Autowired
	private UserService userService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private CertificateService certService;

	@PostMapping(path = "/receive-certificate")
	public ResponseEntity<HttpStatus> receiveCertificate(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		CertificateDistributionDetailsDTO dto = gson.fromJson(data, CertificateDistributionDetailsDTO.class);

		String path = "src/main/resources/certificate/CERT_" + dto.getSerialNum() + ".cer";
		File f = new File(path);

		try {
			Files.write(f.toPath(), dto.getCert());

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/request")
	@PreAuthorize("hasAuthority('PRIVILEGE_CREATE_CSR')")
	public ResponseEntity<byte[]> generateCertificate() throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();

		User u = this.userService.findByEmail(email);

		boolean ok = this.hospitalService.setRequested(u.getHospital().getId());

		if (!ok)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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

		data = "UID=" + u.getId() + ",C=RS, ST=Serbia, L=Serbia, O=Pegasus MS, OU=" + u.getHospital().getName()
				+ ", CN=" + u.getFirstName() + " " + u.getLastName() + ", EMAILADDRESS=" + email + "";
		X500Principal subject = new X500Principal(data);

		ContentSigner signGen = null;
		try {
			signGen = new JcaContentSignerBuilder("SHA1withRSA").build(privateKey);
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		}

		PKCS10CertificationRequestBuilder builder = new JcaPKCS10CertificationRequestBuilder(subject, publicKey);
		PKCS10CertificationRequest csr = builder.build(signGen);
		OutputEncryptor encryptor = null;
		JcaPKCS8Generator generator = new JcaPKCS8Generator(privateKey, encryptor);

		FileWriter fw = new FileWriter(new File("pk.pem"));

		JcaPEMWriter writer = new JcaPEMWriter(fw);
		writer.writeObject(generator);
		writer.close();

		try {
			return new ResponseEntity<byte[]>(csr.getEncoded(), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
