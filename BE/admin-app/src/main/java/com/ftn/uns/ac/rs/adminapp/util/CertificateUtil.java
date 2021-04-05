package com.ftn.uns.ac.rs.adminapp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.core.env.Environment;

import aj.org.objectweb.asm.Attribute;


public class CertificateUtil {

	public static X509Certificate getAdminsCertificate(String store, String pass) {
		
		ArrayList<X509Certificate> certs = new ArrayList<X509Certificate>();
		PrivateKey myPrivateKey = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");

			// Provide location of Java Keystore and password for access
			keyStore.load(new FileInputStream(store), pass.toCharArray());

			// iterate over all aliases
			Enumeration<String> es = keyStore.aliases();
			String alias = "";
			while (es.hasMoreElements()) {
				alias = (String) es.nextElement();
				
				KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
						new KeyStore.PasswordProtection(pass.toCharArray()));

				Certificate[] chain = keyStore.getCertificateChain(alias);

				X509Certificate c = (X509Certificate)chain[0];
				
				return c;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<X509Certificate> getCertificateDetails(String jksPath, String jksPassword) {

		ArrayList<X509Certificate> certs = new ArrayList<X509Certificate>();

		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");

			// Provide location of Java Keystore and password for access
			keyStore.load(new FileInputStream(jksPath), jksPassword.toCharArray());

			// iterate over all aliases
			Enumeration<String> es = keyStore.aliases();
			String alias = "";
			while (es.hasMoreElements()) {
				alias = (String) es.nextElement();
				
				KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
						new KeyStore.PasswordProtection(jksPassword.toCharArray()));

				PrivateKey myPrivateKey = pkEntry.getPrivateKey();
				
				Certificate[] chain = keyStore.getCertificateChain(alias);

				certs.add((X509Certificate)chain[0]);

			}

		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableEntryException e) {
			e.printStackTrace();
		}

		return certs;
	}
	
	public static PrivateKey getAdminsPrivateKey(String jksPath, String jksPassword) {

		ArrayList<X509Certificate> certs = new ArrayList<X509Certificate>();

		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");

			// Provide location of Java Keystore and password for access
			keyStore.load(new FileInputStream(jksPath), jksPassword.toCharArray());

			// iterate over all aliases
			Enumeration<String> es = keyStore.aliases();
			String alias = "";
			while (es.hasMoreElements()) {
				alias = (String) es.nextElement();
				
				KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
						new KeyStore.PasswordProtection(jksPassword.toCharArray()));

				PrivateKey myPrivateKey = pkEntry.getPrivateKey();
				
				return myPrivateKey;

			}

		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableEntryException e) {
			e.printStackTrace();
		}

		return null;
	}

//	public static X509Certificate CSRToCertificate(PKCS10CertificationRequest req, X509Certificate admin, String store, String pass) {
//		JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
//
//        // Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
//        builder = builder.setProvider("BC");
//
//        // Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
//        ContentSigner contentSigner = builder.build(CertificateUtil.getAdminsPrivateKey(store, pass));
//
//        // Postavljaju se podaci za generisanje sertifiakta
//        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(admin.getIssuerX500Principal().getName(),
//                new BigInteger(subjectData.getSerialNumber()),
//                subjectData.getStartDate(),
//                subjectData.getEndDate(),
//                subjectData.getX500name(),
//                subjectData.getPublicKey());
//
//        // Generise se sertifikat
//        X509CertificateHolder certHolder = certGen.build(contentSigner);
//
//        // Builder generise sertifikat kao objekat klase X509CertificateHolder
//        // Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
//        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
//        certConverter = certConverter.setProvider("BC");
//
//        // Konvertuje objekat u sertifikat
//        return certConverter.getCertificate(certHolder);
//	}
	
}
