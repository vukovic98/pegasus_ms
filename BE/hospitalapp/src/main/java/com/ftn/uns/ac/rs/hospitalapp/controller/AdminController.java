package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Admin;
import com.ftn.uns.ac.rs.hospitalapp.dto.UserDetailsDTO;
import com.ftn.uns.ac.rs.hospitalapp.service.AdminService;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.util.AdminDetailsMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.FinalMessage;
import com.ftn.uns.ac.rs.hospitalapp.util.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplMapper;
import com.ftn.uns.ac.rs.hospitalapp.util.PageImplementation;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminDetailsMapper userMapper;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CertificateService certService;
	
	@Autowired
	private LoggerProxy logger;

	@GetMapping(path = "/by-page/{pageNum}")
	public ResponseEntity<FinalMessage> findAll(@PathVariable int pageNum) {

		Gson gson = new Gson();
		
		Pageable pageRequest = PageRequest.of(pageNum, 8);

		Page<Admin> page = this.adminService.findAll(pageRequest);

		List<UserDetailsDTO> adminsDTOS = this.userMapper.listToDTO(page.toList());
		Page<UserDetailsDTO> pageOffersDTOS = new PageImpl<>(adminsDTOS, page.getPageable(), page.getTotalElements());

		PageImplMapper<UserDetailsDTO> pageMapper = new PageImplMapper<>();
		PageImplementation<UserDetailsDTO> pageImpl = pageMapper.toPageImpl(pageOffersDTOS);
		
		String data = gson.toJson(pageImpl);
		
		byte[] compressed_data = EncryptionUtil.compress(data);
		
		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), 
				certService.getMyPrivateKey(), compressed_data, this.env.getProperty("cipherKey"));
		
		this.logger.info("Successful attempt for retrieving admins by admin-app", AdminController.class);
		
		return new ResponseEntity<>(finalMess, HttpStatus.OK);
	}
}
