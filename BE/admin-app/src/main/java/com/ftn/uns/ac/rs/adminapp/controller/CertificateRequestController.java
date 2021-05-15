package com.ftn.uns.ac.rs.adminapp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;
import com.ftn.uns.ac.rs.adminapp.dto.CertificateRequestDTO;
import com.ftn.uns.ac.rs.adminapp.service.CertificateRequestService;
import com.ftn.uns.ac.rs.adminapp.util.CipherEncrypt;
import com.ftn.uns.ac.rs.adminapp.util.LoggerProxy;

@RestController()
@RequestMapping(path = "/certificate-request")
public class CertificateRequestController {

	@Autowired
	private CertificateRequestService certificateReqService;

	@Autowired
	private Environment env;
	
	@Autowired
	private LoggerProxy logger;

	@GetMapping()
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_CSR')")
	public ResponseEntity<ArrayList<CertificateRequestDTO>> getAll() {
		ArrayList<CertificateRequestDTO> dtos = this.certificateReqService.findAll();
		
		this.logger.info("Successfull attempt for reading all CSR-s", CertificateRequestController.class);
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);

	}

	@PostMapping(path = "/denyRequest")
	@PreAuthorize("hasAuthority('PRIVILEGE_DENY_CSR')")
	public ResponseEntity<HttpStatus> denyRequest(@RequestBody long id) {
		CertificateRequest c = this.certificateReqService.findOneById(id);

		if (c != null) {
			boolean ok = this.certificateReqService.remove(c);
			if (ok) {
				
				this.logger.info("Successfull attempt for denying CSR [ " + id + " ]", CertificateRequestController.class);
				
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else {
				
				this.logger.error("[ REMOVING FROM DATABASE ERROR ] Failed attempt for denying CSR [ " + id + " ]", CertificateRequestController.class);

				
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			
			this.logger.error("[ REQUEST NOT FOUND ] Failed attempt for denying CSR [ " + id + " ]", CertificateRequestController.class);
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/verify-request")
	public ResponseEntity<String> verifyRequest(@RequestParam String id) {

		long reqId = Long.parseLong(CipherEncrypt.decrypt(id, env.getProperty("cipherKey")));

		boolean ok = this.certificateReqService.verify(reqId);

		if (ok) {
			String content = "You have successfully verified your request!";
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.TEXT_HTML);

			this.logger.info("Successfull attempt for verifying CSR [ " + reqId + " ]", CertificateRequestController.class);
			
			return new ResponseEntity<>(content, HttpStatus.OK);
		} else {
			
			this.logger.error("[ CSR NOT FOUND ] Failed attempt for verifying CSR [ " + id + " ]", CertificateRequestController.class);
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/{email}")
	public ResponseEntity<Boolean> save(@RequestBody byte[] req, @PathVariable("email") String email) {
		boolean ok = this.certificateReqService.save(req, email);
		if (ok) {
			
			this.logger.info("Successfull attempt for creating CSR for user [ " + email + " ]", CertificateRequestController.class);
			
			return new ResponseEntity<>(ok, HttpStatus.OK);
		}
		else {
						
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
	}
}
