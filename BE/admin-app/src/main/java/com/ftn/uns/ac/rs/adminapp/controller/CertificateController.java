package com.ftn.uns.ac.rs.adminapp.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.dto.CertDetailsDTO;
import com.ftn.uns.ac.rs.adminapp.dto.X509DetailsDTO;
import com.ftn.uns.ac.rs.adminapp.repository.UserRepository;
import com.ftn.uns.ac.rs.adminapp.service.CertificateRequestService;
import com.ftn.uns.ac.rs.adminapp.service.CertificateService;
import com.ftn.uns.ac.rs.adminapp.util.RevokeEntry;

@RestController()
@RequestMapping(path = "/certificate")
public class CertificateController {

	@Autowired
	private CertificateService certService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CertificateRequestService reqService;

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping()
	public ResponseEntity<ArrayList<X509DetailsDTO>> findAll() throws ClassNotFoundException, IOException {
		ArrayList<X509DetailsDTO> listDto = new ArrayList<>();
		ArrayList<X509Certificate> certs = this.certService.findAllCertificates();
		
		for(X509Certificate x : certs) {
			X509DetailsDTO dto = new X509DetailsDTO();
			dto.setSerialNum(x.getSerialNumber().toString());
			dto.setIssuedDate(sdf.format(x.getNotBefore()));
			dto.setIssuer(x.getIssuerX500Principal().getName().split(",")[7].split("=")[1]);
			dto.setSubject(x.getSubjectX500Principal().getName().split(",")[7].split("=")[1]);
			RevokeEntry revoke = certService.isCertificateRevoked(x.getSerialNumber());
			if(revoke.isRevoked()) {
				dto.setValidToDate("REVOKED");
				dto.setRevoked(true);
			}
			else {
				dto.setValidToDate(sdf.format(x.getNotAfter()));
				dto.setRevoked(false);
			}
			
			listDto.add(dto);
		}
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}
	
	@PostMapping("/generateCRL")
	public ResponseEntity<String> generateCRL() throws CertificateException, CRLException, NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, OperatorCreationException, IOException{
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();
		
		User user = userRepository.findByEmail(username);
		
		certService.generateCRL(user);
		return ResponseEntity.ok().body("Success");
		
	}
	
	@PostMapping("/revokeCertificate")
	public ResponseEntity<String> revokeCertificate(@RequestParam("serialNumber") long serialNumber, @RequestParam("revokeReason") int revokeReason) throws CertificateException, CRLException, NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, OperatorCreationException, IOException, ClassNotFoundException{
				
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();
		
		User user = userRepository.findByEmail(username);
		
		certService.revokeCertificate(user, BigInteger.valueOf(serialNumber), revokeReason);
		return ResponseEntity.ok().body("Success");
		
		
	}
	
	@GetMapping("/checkRevoked")
	public ResponseEntity<String> checkCertificateRevoked(@RequestParam("serialNumber") BigInteger serialNumber) throws ClassNotFoundException, IOException{
		
		if(certService.isCertificateRevoked(serialNumber).isRevoked())
			return ResponseEntity.ok().body("This certificate has been revoked");
		else
			return ResponseEntity.ok().body("This certificate is active");
	}
	
	@GetMapping("/readCRL")
	public ResponseEntity<String> readCRL() throws ClassNotFoundException, IOException{
		certService.readCRL();
		return ResponseEntity.ok().body("Success");
	}
	

//	@PostMapping(path = "/generateCertificate")
//	public ResponseEntity<X509Certificate> generateCertificate(@RequestBody long id) {
//		CertificateRequest req = this.reqService.findOneById(id);
//
//		if (req != null) {
//			PKCS10CertificationRequest crt = null;
//			try {
//				crt = new PKCS10CertificationRequest(req.getCertificateRequest());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			
//			
//		} else
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	}

	@GetMapping("/getOne")
	public ResponseEntity<CertDetailsDTO> findOne(@RequestParam("serialNumber") BigInteger serialNumber) throws ClassNotFoundException, IOException {

		ArrayList<X509Certificate> certs = this.certService.findAllCertificates();
		CertDetailsDTO dto = null;
		for (X509Certificate x : certs) {
			if (x.getSerialNumber().equals(serialNumber)) {
				dto = new CertDetailsDTO();
				String[] issuerList = x.getIssuerDN().getName().split(",");
				String[] subjectList = x.getSubjectDN().getName().split(",");
				dto.setSerialNum(x.getSerialNumber().toString());
				dto.setIssuedDate(sdf.format(x.getNotBefore()));
				RevokeEntry revoke = certService.isCertificateRevoked(x.getSerialNumber());
				if(revoke.isRevoked()) {
					dto.setValidToDate("REVOKED");
					dto.setRevoked(true);
					dto.setRevokedReason(revoke.toString());
				}
				else {
					dto.setValidToDate(sdf.format(x.getNotAfter()));
					dto.setRevoked(false);
				}
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
