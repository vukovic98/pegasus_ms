package com.ftn.uns.ac.rs.adminapp.util;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateGenerator {
	public CertificateGenerator() {
		Security.addProvider(new BouncyCastleProvider());
	}

	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, String template) {
		try {
			// Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni
			// kljuc pravi se builder za objekat
			// Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za
			// potpisivanje sertifikata
			// Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje
			// sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

			// Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			// Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za
			// potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			// Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()), subjectData.getStartDate(), subjectData.getEndDate(),
					subjectData.getX500name(), subjectData.getPublicKey());

			// Extensions
			if (template.equalsIgnoreCase("Certificate Authority")) {
				try {
					certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
				} catch (CertIOException e) {
					e.printStackTrace();
				}
			}

			if (template.equalsIgnoreCase("End User")) {
				try {
					certGen.addExtension(Extension.keyUsage, true,
							new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
				} catch (CertIOException e) {
					e.printStackTrace();
				}
			}

			if (template.equalsIgnoreCase("Server Side User")) {
				try {
					certGen.addExtension(Extension.keyUsage, true, new KeyUsage(
							KeyUsage.digitalSignature | KeyUsage.keyEncipherment | KeyUsage.nonRepudiation));
				} catch (CertIOException e) {
					e.printStackTrace();
				}
			}

			if (template.equalsIgnoreCase("Client Side User")) {
				try {
					certGen.addExtension(Extension.keyUsage, true,
							new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyAgreement | KeyUsage.nonRepudiation));
				} catch (CertIOException e) {
					e.printStackTrace();
				}
			}

			// Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			// Builder generise sertifikat kao objekat klase X509CertificateHolder
			// Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se
			// koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			// Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (IllegalArgumentException | IllegalStateException | OperatorCreationException
				| CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public X509Certificate generateSelfSignedCertificate(KeyPair keyPair, SubjectData subjectData) {
		try {
			// Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni
			// kljuc pravi se builder za objekat
			// Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za
			// potpisivanje sertifikata
			// Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje
			// sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

			// Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			// Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za
			// potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(keyPair.getPrivate());

			// Postavljaju se podaci za generisanje sertifiakta

			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(subjectData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()), subjectData.getStartDate(), subjectData.getEndDate(),
					subjectData.getX500name(), subjectData.getPublicKey());

			// Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			// Builder generise sertifikat kao objekat klase X509CertificateHolder
			// Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se
			// koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			// Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (IllegalArgumentException | IllegalStateException | OperatorCreationException
				| CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}
}
