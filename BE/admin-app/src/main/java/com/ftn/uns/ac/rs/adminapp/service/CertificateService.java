package com.ftn.uns.ac.rs.adminapp.service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.X509CRLEntryHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.dto.CertificateDistributionDetailsDTO;
import com.ftn.uns.ac.rs.adminapp.dto.IssuerData;
import com.ftn.uns.ac.rs.adminapp.repository.CertificateRepository;
import com.ftn.uns.ac.rs.adminapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.adminapp.util.FinalMessage;
import com.ftn.uns.ac.rs.adminapp.util.RevokeEntry;
import com.ftn.uns.ac.rs.adminapp.util.keystores.KeyStoreReader;
import com.google.gson.Gson;

@Service
public class CertificateService {

	@Autowired
	private Environment env;

	@Autowired
	private CertificateRepository certRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public ArrayList<X509Certificate> findAllCertificates() {
		return this.certRepository.findAllCertificates();
	}

	public PublicKey getBobsPublicKey() {
		return this.certRepository.getBobsPublicKey();
	}

	public PrivateKey getMyPrivateKey() {
		return this.certRepository.getMyPrivateKey();
	}

	public void generateCRL(User user) throws CertificateException, CRLException, IOException, NoSuchAlgorithmException,
			UnrecoverableEntryException, KeyStoreException, OperatorCreationException {

		KeyStoreReader reader = new KeyStoreReader();
		IssuerData issuer = reader.readIssuerFromStore("src/main/resources/static/super_cert/super_admin.jks", "super_admin",
				"vukovic".toCharArray(), "vukovic".toCharArray());

		X509v2CRLBuilder builder = new X509v2CRLBuilder(issuer.getX500name(), Date.from(Instant.now()));

		ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(issuer.getPrivateKey());
		X509CRLHolder crl = builder.build(contentSigner);

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("src/main/resources/static/crl/crl.bin")));
		oos.writeObject(crl);
		oos.close();
	}

	public void revokeCertificate(User user, BigInteger certificateSN, int reason)
			throws OperatorCreationException, IOException, ClassNotFoundException {

		KeyStoreReader reader = new KeyStoreReader();
		IssuerData issuer = reader.readIssuerFromStore("src/main/resources/static/super_cert/super_admin.jks", "super_admin",
				"vukovic".toCharArray(), "vukovic".toCharArray());

		X509v2CRLBuilder builder = new X509v2CRLBuilder(issuer.getX500name(), Date.from(Instant.now()));

		FileInputStream fstream = new FileInputStream(new File("src/main/resources/static/crl/crl.bin"));
		try {
			ObjectInputStream ois = new ObjectInputStream(fstream);

			while (true) {
				X509CRLHolder obj;
				try {
					obj = (X509CRLHolder) ois.readObject();
				} catch (EOFException e) {
					break;
				}

				for (Object entry : obj.getRevokedCertificates().toArray()) {
					X509CRLEntryHolder entryHolder = (X509CRLEntryHolder) entry;
					ASN1Enumerated reasonCode = (ASN1Enumerated) ASN1Enumerated
							.getInstance(entryHolder.getExtension(Extension.reasonCode).getParsedValue());
					builder.addCRLEntry(entryHolder.getSerialNumber(), entryHolder.getRevocationDate(),
							reasonCode.getValue().intValue());
				}

				builder.addCRLEntry(certificateSN, Date.from(Instant.now()), reason);

				ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA")
						.build(issuer.getPrivateKey());
				X509CRLHolder crl = builder.build(contentSigner);

				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("crl.bin")));
				oos.writeObject(crl);
				oos.close();

			}
		} catch (StreamCorruptedException e) {
		} finally {
			fstream.close();

		}

	}

	public void readCRL() throws IOException, ClassNotFoundException {

		FileInputStream fstream = new FileInputStream(new File("crl.bin"));
		try {
			ObjectInputStream ois = new ObjectInputStream(fstream);

			while (true) {
				X509CRLHolder obj;
				try {
					obj = (X509CRLHolder) ois.readObject();
				} catch (EOFException e) {
					break;
				}

				for (Object entry : obj.getRevokedCertificates().toArray()) {
					X509CRLEntryHolder entryHolder = (X509CRLEntryHolder) entry;
					System.out.println(entryHolder.getSerialNumber());
					System.out.println(entryHolder.getRevocationDate().toString());
				}

			}
		} finally {
			fstream.close();

		}

	}

	public RevokeEntry isCertificateRevoked(BigInteger certificateSN) throws ClassNotFoundException, IOException {

		FileInputStream fstream = new FileInputStream(new File("crl.bin"));
		try {
			ObjectInputStream ois = new ObjectInputStream(fstream);

			while (true) {
				X509CRLHolder obj;
				try {
					obj = (X509CRLHolder) ois.readObject();
				} catch (EOFException e) {
					break;
				}

				for (Object entry : obj.getRevokedCertificates().toArray()) {
					X509CRLEntryHolder entryHolder = (X509CRLEntryHolder) entry;

					if (entryHolder.getSerialNumber().equals(certificateSN)) {
						ASN1Enumerated reasonCode = (ASN1Enumerated) ASN1Enumerated
								.getInstance(entryHolder.getExtension(Extension.reasonCode).getParsedValue());
						
						fstream.close();
						ois.close();
						
						return new RevokeEntry(true, reasonCode.getValue().intValue());
					}
				}

			}
		} finally {
			fstream.close();

		}
		return new RevokeEntry(false, 0);
	}

	public void distributeCertificate(CertificateDistributionDetailsDTO dto) {
		File f = new File(dto.getPath());
		byte[] file = null;
		try {
			file = Files.readAllBytes(f.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (file != null) {
			Gson gson = new Gson();
			dto.setCert(file);
			dto.setPath("");

			String data = gson.toJson(dto);

			byte[] compressed_data = EncryptionUtil.compress(data);

			FinalMessage finalMess = EncryptionUtil.encrypt(this.certRepository.getBobsPublicKey(),
					this.certRepository.getMyPrivateKey(), compressed_data, this.env.getProperty("cipherKey"));

			HttpEntity<FinalMessage> request = new HttpEntity<FinalMessage>(finalMess);

			ResponseEntity<HttpStatus> responseEntityStr = restTemplate
					.postForEntity("https://localhost:8081/certificate/receive-certificate", request, HttpStatus.class);

		}

	}

}
