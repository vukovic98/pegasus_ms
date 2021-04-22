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
import com.ftn.uns.ac.rs.adminapp.util.PageImplementation;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/doctor")
public class DoctorController {
	
	@Autowired
	private CertificateService certService;

	@GetMapping(path = "/by-page/{pageNum}")
	@PreAuthorize("hasAuthority('PRIVILEGE_READ_USERS')")
	public ResponseEntity<PageImplementation<UserDetailsDTO>> findAll(@PathVariable int pageNum) {
		Gson gson = new Gson();
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<FinalMessage> responseEntity = 
				restTemplate.exchange("https://localhost:8081/doctor/by-page/" + pageNum, HttpMethod.GET, null,
						new ParameterizedTypeReference<FinalMessage>() {
						});

		FinalMessage finalMess = responseEntity.getBody();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBobsPublicKey(), certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);
		
		@SuppressWarnings("serial")
		PageImplementation<UserDetailsDTO> dtos = gson.fromJson(data, new TypeToken<PageImplementation<UserDetailsDTO>>(){}.getType());
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
}
