package com.ftn.uns.ac.rs.hospitalapp.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.hospitalapp.dto.CertificateRevokedDTO;

public class CertificateUtil {
	
	
	public static PublicKey getBobsPublicKey(String store) throws CertificateRevokedException {
		try {
			
			String allPassword = "vukovic";

			String keyStore = "src/main/resources/certificate/server.pfx";
			
			SSLContext sslContext = SSLContextBuilder.create()
					.loadKeyMaterial(ResourceUtils.getFile(keyStore), allPassword.toCharArray(), allPassword.toCharArray())
					.build();

			
			HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(client);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			CertificateFactory fact = CertificateFactory.getInstance("X.509");
			FileInputStream is = new FileInputStream(store);
			X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
			
			if(cer.getSerialNumber() != null) {
			
				ResponseEntity<CertificateRevokedDTO> responseEntity = restTemplate.getForEntity("https://localhost:8080/certificate/checkRevokedExternal?serialNumber={serialNumber}", CertificateRevokedDTO.class, cer.getSerialNumber());
				
				System.out.println(responseEntity.getStatusCodeValue());
				System.out.println(responseEntity.getBody().isRevoked());
				
				if (responseEntity.getBody().isRevoked()) {
					
					throw new CertificateRevokedException();
					
					
				}
			}
			PublicKey key = cer.getPublicKey();
			is.close();
			
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getClass().equals(CertificateRevokedException.class)) {
				throw (CertificateRevokedException) e;
			}
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
