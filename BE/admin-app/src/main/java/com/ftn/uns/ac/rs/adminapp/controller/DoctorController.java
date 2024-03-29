package com.ftn.uns.ac.rs.adminapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.adminapp.dto.UserDetailsDTO;
import com.ftn.uns.ac.rs.adminapp.service.CertificateService;
import com.ftn.uns.ac.rs.adminapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.adminapp.util.FinalMessage;
import com.ftn.uns.ac.rs.adminapp.util.LoggerProxy;
import com.ftn.uns.ac.rs.adminapp.util.PageImplementation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping(path = "/doctor")
public class DoctorController {
	
	@Autowired
	private CertificateService certService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoggerProxy logger;
	
	@GetMapping(path = "/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_USERS')")
	public ResponseEntity<PageImplementation<UserDetailsDTO>> findAll(@PathVariable int pageNum) {
		Gson gson = new Gson();
		
		
		ResponseEntity<FinalMessage> responseEntity = 
				restTemplate.exchange("https://localhost:8081/doctor/by-page/" + pageNum, HttpMethod.GET, null,
						new ParameterizedTypeReference<FinalMessage>() {
						});

		FinalMessage finalMess = responseEntity.getBody();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(), certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);
		
		PageImplementation<UserDetailsDTO> dtos = gson.fromJson(data, new TypeToken<PageImplementation<UserDetailsDTO>>(){}.getType());
		
		this.logger.info("Successfull attempt for retrieving doctors from hospital-app", DoctorController.class);
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
}
