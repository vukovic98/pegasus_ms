package com.ftn.uns.ac.rs.adminapp.controller;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;
import com.ftn.uns.ac.rs.adminapp.dto.CertDetailsDTO;
import com.ftn.uns.ac.rs.adminapp.dto.X509DetailsDTO;
import com.ftn.uns.ac.rs.adminapp.service.CertificateRequestService;
import com.ftn.uns.ac.rs.adminapp.service.CertificateService;
import com.ftn.uns.ac.rs.adminapp.service.SerialKeyCounterService;
import com.ftn.uns.ac.rs.adminapp.util.CertificateUtil;
import com.ftn.uns.ac.rs.adminapp.util.IssuerData;
import com.ftn.uns.ac.rs.adminapp.util.SubjectData;

@RestController()
@RequestMapping(path = "/certificate")
public class CertificateController {

	@Autowired
	private CertificateService certService;
	
	@Autowired
	private Environment env;

	@Autowired
	private CertificateRequestService reqService;
	
	@Autowired
	private SerialKeyCounterService counterService;

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping()
	public ResponseEntity<ArrayList<X509DetailsDTO>> findAll() {
		ArrayList<X509DetailsDTO> listDto = new ArrayList<>();
		ArrayList<X509Certificate> certs = this.certService.findAllCertificates();

		for (X509Certificate x : certs) {
			X509DetailsDTO dto = new X509DetailsDTO();
			dto.setSerialNum(x.getSerialNumber().toString());
			dto.setIssuedDate(sdf.format(x.getNotBefore()));
			dto.setIssuer(x.getIssuerX500Principal().getName().split(",")[7].split("=")[1]);
			dto.setSubject(x.getSubjectX500Principal().getName().split(",")[7].split("=")[1]);
			dto.setValidToDate(sdf.format(x.getNotAfter()));

			listDto.add(dto);
		}
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@PostMapping(path = "/generateCertificate")
	public ResponseEntity<X509Certificate> generateCertificate(@RequestBody long id) {
		CertificateRequest req = this.reqService.findOneById(id);

		if (req != null) {
			PKCS10CertificationRequest crt = null;
			try {
				crt = new PKCS10CertificationRequest(req.getCertificateRequest());
				PrivateKey adminsKey = CertificateUtil.getAdminsPrivateKey(env.getProperty("admin.store"), env.getProperty("admin.password"));
				X509Certificate adminsCert = CertificateUtil.getAdminsCertificate(env.getProperty("admin.store"), env.getProperty("admin.password"));
				
//				Issuer Data
				
				IssuerData adminData = new IssuerData();
				adminData.setPrivateKey(adminsKey);
				X500Name x500name = new JcaX509CertificateHolder(adminsCert).getSubject();
				adminData.setX500name(x500name);
				
//				Subject Data
				
				SubjectData subject = new SubjectData();
				SubjectPublicKeyInfo pkInfo = crt.getSubjectPublicKeyInfo();
				JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
				PublicKey pubKey = converter.getPublicKey(pkInfo);
				
				subject.setPublicKey(pubKey);
				subject.setStartDate(new Date());
				long serialNum = this.counterService.getNextValue();
				if(serialNum != -1)
					subject.setSerialNumber(serialNum+"");
				else
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
				Date currentDate = new Date();
		        Calendar c = Calendar.getInstance();
		        c.setTime(currentDate);
		        c.add(Calendar.YEAR, Integer.parseInt(env.getProperty("certificate.duration")));
		        
		        subject.setEndDate(c.getTime());
		        
		        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
				builder.addRDN(BCStyle.CN, "Vladimir Vukovic");
				builder.addRDN(BCStyle.SURNAME, "Vukovic");
				builder.addRDN(BCStyle.GIVENNAME, "Vladimir");
				builder.addRDN(BCStyle.O, "Pegasus MS");
				builder.addRDN(BCStyle.OU, "Super admin");
				builder.addRDN(BCStyle.C, "RS");
				builder.addRDN(BCStyle.E, "vladimirvukovic98@hotmail.rs");

				// UID (USER ID) je ID korisnika
				builder.addRDN(BCStyle.UID, "111111");
				
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			
			
		} else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/getOne")
	public ResponseEntity<CertDetailsDTO> findOne(@RequestParam("serialNumber") BigInteger serialNumber) {

		ArrayList<X509Certificate> certs = this.certService.findAllCertificates();
		CertDetailsDTO dto = null;
		for (X509Certificate x : certs) {
			if (x.getSerialNumber().equals(serialNumber)) {
				dto = new CertDetailsDTO();
				String[] issuerList = x.getIssuerDN().getName().split(",");
				String[] subjectList = x.getSubjectDN().getName().split(",");
				dto.setSerialNum(x.getSerialNumber().toString());
				dto.setIssuedDate(sdf.format(x.getNotBefore()));
				dto.setValidToDate(sdf.format(x.getNotAfter()));
				dto.setIssuerCN(issuerList[7].split("=")[1]);
				dto.setIssuerEmail(issuerList[1].split("=")[1]);
				dto.setIssuerID(issuerList[0].split("=")[1]);
				dto.setIssuerOU(issuerList[3].split("=")[1]);
				dto.setSubjectCN(subjectList[7].split("=")[1]);
				dto.setSubjectID(subjectList[0].split("=")[1]);
				dto.setSubjectEmail(subjectList[1].split("=")[1]);
				dto.setSubjectOU(subjectList[3].split("=")[1]);
				break;
			}

		}
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
