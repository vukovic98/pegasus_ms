package com.ftn.uns.ac.rs.adminapp.service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.cert.X509CRLEntryHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.dto.IssuerData;
import com.ftn.uns.ac.rs.adminapp.repository.CertificateRepository;
import com.ftn.uns.ac.rs.adminapp.util.keystores.KeyStoreReader;

@Service
public class CertificateService {

	@Autowired
	private CertificateRepository certRepository;
	
	public ArrayList<X509Certificate> findAllCertificates() {
		return this.certRepository.findAllCertificates();
	}
	
	public void generateCRL(User user) throws CertificateException, CRLException, IOException, NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, OperatorCreationException {
		
		KeyStoreReader reader = new KeyStoreReader();
		IssuerData issuer = reader.readIssuerFromStore("src/main/resources/static/super_cert/super_admin.jks", "1", "vukovic".toCharArray(), "vukovic".toCharArray());
		
		/*
        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, "Marija Kovacevic");
        nameBuilder.addRDN(BCStyle.SURNAME, "Kovacevic");
        nameBuilder.addRDN(BCStyle.GIVENNAME, "Marija");
        nameBuilder.addRDN(BCStyle.O, "UNS-FTN");
        nameBuilder.addRDN(BCStyle.OU, "Katedra za informatiku");
        nameBuilder.addRDN(BCStyle.C, "RS");
        nameBuilder.addRDN(BCStyle.E, "marija.kovacevic@uns.ac.rs");

        // UID (USER ID) je ID korisnika
        nameBuilder.addRDN(BCStyle.UID, "654321");*/
		
		
		
		X509v2CRLBuilder builder = new X509v2CRLBuilder(issuer.getX500name(), Date.from(Instant.now()));
		builder.addCRLEntry(BigInteger.ONE, Date.from(Instant.now()), CRLReason.privilegeWithdrawn);
		
		ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(issuer.getPrivateKey());
		X509CRLHolder crl = builder.build(contentSigner);
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("crl.bin")));
		oos.writeObject(crl);
		oos.close();
	}
	
	public void revokeCertificate(User user, BigInteger certificateSN) throws OperatorCreationException, IOException, ClassNotFoundException {
		
		KeyStoreReader reader = new KeyStoreReader();
		IssuerData issuer = reader.readIssuerFromStore("src/main/resources/static/super_cert/super_admin.jks", "1", "vukovic".toCharArray(), "vukovic".toCharArray());

		/*
        X500NameBuilder nameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
        nameBuilder.addRDN(BCStyle.CN, "Marija Kovacevic");
        nameBuilder.addRDN(BCStyle.SURNAME, "Kovacevic");
        nameBuilder.addRDN(BCStyle.GIVENNAME, "Marija");
        nameBuilder.addRDN(BCStyle.O, "UNS-FTN");
        nameBuilder.addRDN(BCStyle.OU, "Katedra za informatiku");
        nameBuilder.addRDN(BCStyle.C, "RS");
        nameBuilder.addRDN(BCStyle.E, "marija.kovacevic@uns.ac.rs");

        // UID (USER ID) je ID korisnika
        nameBuilder.addRDN(BCStyle.UID, "654321");
		*/

        
		X509v2CRLBuilder builder = new X509v2CRLBuilder(issuer.getX500name(), Date.from(Instant.now()));
		
		FileInputStream fstream = new FileInputStream(new File("crl.bin"));
		try {
		  ObjectInputStream ois = new ObjectInputStream(fstream);
		  
		  while (true) {
			  X509CRLHolder obj;
		    try {
		      obj = (X509CRLHolder)ois.readObject();
		    } catch (EOFException e) {
		      break;
		    }
		   
		    for (Object entry : obj.getRevokedCertificates().toArray()) {
				X509CRLEntryHolder entryHolder = (X509CRLEntryHolder)entry;
				builder.addCRLEntry(entryHolder.getSerialNumber(), entryHolder.getRevocationDate(), CRLReason.privilegeWithdrawn);
			}
		    builder.addCRLEntry(certificateSN, Date.from(Instant.now()), CRLReason.privilegeWithdrawn);
		    
			ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(issuer.getPrivateKey());
			X509CRLHolder crl = builder.build(contentSigner);
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("crl.bin")));
			oos.writeObject(crl);
			oos.close();
		    
		  }
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
		      obj = (X509CRLHolder)ois.readObject();
		    } catch (EOFException e) {
		      break;
		    }
		   
		    for (Object entry : obj.getRevokedCertificates().toArray()) {
				X509CRLEntryHolder entryHolder = (X509CRLEntryHolder)entry;
				System.out.println(entryHolder.getSerialNumber());
				System.out.println(entryHolder.getRevocationDate().toString());
			}
		    
		  }
		} finally {
		  fstream.close();
		  
		}
		
	}
	
	public boolean isCertificateRevoked(BigInteger certificateSN) throws ClassNotFoundException, IOException {
		
		FileInputStream fstream = new FileInputStream(new File("crl.bin"));
		try {
		  ObjectInputStream ois = new ObjectInputStream(fstream);
		  
		  while (true) {
			  X509CRLHolder obj;
		    try {
		      obj = (X509CRLHolder)ois.readObject();
		    } catch (EOFException e) {
		      break;
		    }
		   
		    for (Object entry : obj.getRevokedCertificates().toArray()) {
				X509CRLEntryHolder entryHolder = (X509CRLEntryHolder)entry;
				
				if(entryHolder.getSerialNumber().equals(certificateSN))
					return true;
			}
		    
		  }
		} finally {
		  fstream.close();
		  
		}
		return false;
	}
	
}
