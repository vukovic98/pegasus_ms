package com.ftn.uns.ac.rs.blooddevice.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificateUtil {

	public static PublicKey getBobsPublicKey(String store) {
		try {
			CertificateFactory fact = CertificateFactory.getInstance("X.509");
			FileInputStream is = new FileInputStream(store);
			X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
			PublicKey key = cer.getPublicKey();
			is.close();

			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PrivateKey getMyPrivateKey(String store, String pass) {
		try {
			KeyStore p12 = KeyStore.getInstance("pkcs12");

			// getPassword returns the password of the key / file. The password should not
			// be hard coded.
			p12.load(new FileInputStream(store), pass.toCharArray());

			// the key is ready to be used !
			PrivateKey key2 = (PrivateKey) p12.getKey("1", pass.toCharArray());

			return key2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
