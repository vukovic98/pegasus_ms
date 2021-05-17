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
import com.ftn.uns.ac.rs.hospitalapp.util.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/device")
public class DeviceController {
	
	@Autowired
	private CertificateService certService;
	
	@Autowired
	private LoggerProxy logger;

	@PostMapping(path = "/blood-device")
	public ResponseEntity<HttpStatus> bloodDeviceData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();
		
		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getBloodDevicePublicKey(), certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);
		
		BloodData bloodData = gson.fromJson(data, BloodData.class);
		
		this.logger.info("Successfully received blood device data", DeviceController.class);
		
		System.out.println(bloodData);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(path = "/neurological-device")
	public ResponseEntity<HttpStatus> neurologicalDeviceData(@RequestBody FinalMessage finalMess) {

		Gson gson = new Gson();
		
		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getNeurologicalDevicePublicKey(), certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);
		
		NeurologicalData neuroData = gson.fromJson(data, NeurologicalData.class);
		
		this.logger.info("Successfully received neurological device data", DeviceController.class);
		
		System.out.println(neuroData);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
