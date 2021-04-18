package com.ftn.uns.ac.rs.adminapp.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;
import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.dto.CertDetailsDTO;
import com.ftn.uns.ac.rs.adminapp.dto.CertificateDistributionDetailsDTO;
import com.ftn.uns.ac.rs.adminapp.dto.IssueCertificateDTO;
import com.ftn.uns.ac.rs.adminapp.dto.X509DetailsDTO;
import com.ftn.uns.ac.rs.adminapp.repository.UserRepository;
import com.ftn.uns.ac.rs.adminapp.service.CertificateRequestService;
import com.ftn.uns.ac.rs.adminapp.service.CertificateService;
import com.ftn.uns.ac.rs.adminapp.service.SerialKeyCounterService;
import com.ftn.uns.ac.rs.adminapp.util.CertificateGenerator;
import com.ftn.uns.ac.rs.adminapp.util.CertificateUtil;
import com.ftn.uns.ac.rs.adminapp.util.IssuerData;
import com.ftn.uns.ac.rs.adminapp.util.KeyStoreWriter;
import com.ftn.uns.ac.rs.adminapp.util.RevokeEntry;
import com.ftn.uns.ac.rs.adminapp.util.SubjectData;

@RestController()
@RequestMapping(path = "/certificate")
public class CertificateController {

	@Autowired
	private CertificateService certService;
	@Autowired
	private Environment env;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CertificateRequestService reqService;

	@Autowired
	private SerialKeyCounterService counterService;

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping()
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<ArrayList<X509DetailsDTO>> findAll() throws ClassNotFoundException, IOException {
		ArrayList<X509DetailsDTO> listDto = new ArrayList<>();
		ArrayList<X509Certificate> certs = this.certService.findAllCertificates();

		for (X509Certificate x : certs) {

			X509DetailsDTO dto = new X509DetailsDTO();
			dto.setSerialNum(x.getSerialNumber().toString());
			dto.setIssuedDate(sdf.format(x.getNotBefore()));
			try {
				dto.setIssuer(x.getIssuerX500Principal().getName().split(",")[0].split("=")[1]);
			} catch (Exception e) {
				dto.setIssuer(x.getIssuerX500Principal().getName());
			}
			try {
				dto.setSubject(x.getSubjectDN().getName().split(",")[0].split("=")[1]);
			} catch (Exception e) {
				dto.setSubject(x.getSubjectDN().getName());
			}
			RevokeEntry revoke = certService.isCertificateRevoked(x.getSerialNumber());
			if (revoke.isRevoked()) {
				dto.setValidToDate("REVOKED");
				dto.setRevoked(true);
			} else {
				dto.setValidToDate(sdf.format(x.getNotAfter()));
				dto.setRevoked(false);
			}

			listDto.add(dto);
		}
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@PostMapping("/generateCRL")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<String> generateCRL() throws CertificateException, CRLException, NoSuchAlgorithmException,
			UnrecoverableEntryException, KeyStoreException, OperatorCreationException, IOException {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = userRepository.findByEmail(username);

		certService.generateCRL(user);
		return ResponseEntity.ok().body("Success");

	}

	@PostMapping("/revokeCertificate")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<String> revokeCertificate(@RequestParam("serialNumber") long serialNumber,
			@RequestParam("revokeReason") int revokeReason)
			throws CertificateException, CRLException, NoSuchAlgorithmException, UnrecoverableEntryException,
			KeyStoreException, OperatorCreationException, IOException, ClassNotFoundException {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = userRepository.findByEmail(username);

		certService.revokeCertificate(user, BigInteger.valueOf(serialNumber), revokeReason);
		return ResponseEntity.ok().body("Success");

	}

	@GetMapping("/checkRevoked")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<String> checkCertificateRevoked(@RequestParam("serialNumber") BigInteger serialNumber)
			throws ClassNotFoundException, IOException {

		if (certService.isCertificateRevoked(serialNumber).isRevoked())
			return ResponseEntity.ok().body("This certificate has been revoked");
		else
			return ResponseEntity.ok().body("This certificate is active");
	}

	@GetMapping("/readCRL")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<String> readCRL() throws ClassNotFoundException, IOException {
		certService.readCRL();
		return ResponseEntity.ok().body("Success");
	}

	@PostMapping(path = "/generateCertificate")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<X509Certificate> generateCertificate(@RequestBody IssueCertificateDTO dto) {
		CertificateRequest req = this.reqService.findOneById(dto.getId());

		if (req != null) {
			PKCS10CertificationRequest crt = null;
			try {
				crt = new PKCS10CertificationRequest(req.getCertificateRequest());
				PrivateKey adminsKey = CertificateUtil.getAdminsPrivateKey(env.getProperty("admin.store"),
						env.getProperty("admin.password"));
				X509Certificate adminsCert = CertificateUtil.getAdminsCertificate(env.getProperty("admin.store"),
						env.getProperty("admin.password"));

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
				if (serialNum != -1)
					subject.setSerialNumber(serialNum + "");
				else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				Date currentDate = new Date();
				Calendar c = Calendar.getInstance();
				c.setTime(currentDate);
				c.add(Calendar.YEAR, Integer.parseInt(env.getProperty("certificate.duration")));

				subject.setEndDate(c.getTime());

//		        data = "UID=" + u.getId() + ",C=RS, ST=Serbia, L=Serbia, O=Pegasus MS, OU=" + u.getHospital().getName() + ", CN=" + u.getFirstName() + " " + u.getLastName()
//				+ ", EMAILADDRESS=" + email + "";

				RDN[] data = crt.getSubject().getRDNs();

				X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
				builder.addRDN(BCStyle.CN, data[1].getFirst().getValue());
				builder.addRDN(BCStyle.SURNAME, data[1].getFirst().getValue().toString().split(" ")[1]);
				builder.addRDN(BCStyle.GIVENNAME, data[1].getFirst().getValue().toString().split(" ")[0]);
				builder.addRDN(BCStyle.O, data[3].getFirst().getValue());
				builder.addRDN(BCStyle.OU, data[2].getFirst().getValue());
				builder.addRDN(BCStyle.C, data[6].getFirst().getValue());
				builder.addRDN(BCStyle.ST, data[4].getFirst().getValue());
				builder.addRDN(BCStyle.L, data[5].getFirst().getValue());
				builder.addRDN(BCStyle.E, data[0].getFirst().getValue());

				builder.addRDN(BCStyle.UID, data[7].getFirst().getValue());

				X500Name name = builder.build();

				subject.setX500name(name);

				CertificateGenerator gen = new CertificateGenerator();

				X509Certificate cert = gen.generateCertificate(subject, adminData, dto.getTemplate());

				KeyStoreWriter cw = new KeyStoreWriter();

				try {
					cw.loadKeyStore(env.getProperty("jks.store"), env.getProperty("jks.password").toCharArray());

					cw.write(subject.getSerialNumber(), adminData.getPrivateKey(),
							env.getProperty("jks.password").toCharArray(), cert);
					cw.saveKeyStore(env.getProperty("jks.store"), env.getProperty("jks.password").toCharArray());
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				this.reqService.remove(req);

//				Writing certificate to .cer file
				CertificateDistributionDetailsDTO dtoDis = CertificateUtil.writeCertificateToFile(cert);

				this.certService.distributeCertificate(dtoDis);

				return new ResponseEntity<>(HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/test")
	public ResponseEntity<HttpStatus> test() {
		CertificateDistributionDetailsDTO d = new CertificateDistributionDetailsDTO(
				"src/main/resources/certificates/CERT_55.cer", new BigInteger("12"), null);
		this.certService.distributeCertificate(d);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/getOne")
	@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	public ResponseEntity<CertDetailsDTO> findOne(@RequestParam("serialNumber") BigInteger serialNumber)
			throws ClassNotFoundException, IOException {

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
				if (revoke.isRevoked()) {
					dto.setValidToDate("REVOKED");
					dto.setRevoked(true);
					dto.setRevokedReason(revoke.toString());
				} else {
					dto.setValidToDate(sdf.format(x.getNotAfter()));
					dto.setRevoked(false);
				}
				dto.setIssuerCN(issuerList[1].split("=")[1]);
				dto.setIssuerEmail(issuerList[0].split("=")[1]);
				dto.setIssuerID(issuerList[1].split("=")[1]);
				dto.setIssuerOU(issuerList[3].split("=")[1]);
				dto.setSubjectCN(subjectList[1].split("=")[1]);
				dto.setSubjectID(subjectList[1].split("=")[1]);
				dto.setSubjectEmail(subjectList[0].split("=")[1]);
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
