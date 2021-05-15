package com.ftn.uns.ac.rs.hospitalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;
import com.ftn.uns.ac.rs.hospitalapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.FinalMessage;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/device")
public class DeviceController {
	
	@Autowired
	private CertificateService certService;

	@PostMapping(path = "/blood-device")
	public ResponseEntity<HttpStatus> bloodDeviceData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();
		
		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBloodDevicePublicKey(), certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);
		
		BloodData bloodData = gson.fromJson(data, BloodData.class);
		
		System.out.println(bloodData);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
