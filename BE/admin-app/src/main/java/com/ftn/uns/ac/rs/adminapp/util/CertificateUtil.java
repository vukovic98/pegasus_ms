package com.ftn.uns.ac.rs.adminapp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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


public class CertificateUtil {
	
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

}
