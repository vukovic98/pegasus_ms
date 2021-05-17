package com.ftn.uns.ac.rs.unknowndevice.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.unknowndevice.repository.CertificateRepository;
import com.ftn.uns.ac.rs.unknowndevice.util.EncryptionUtil;
import com.ftn.uns.ac.rs.unknowndevice.util.FinalMessage;
import com.ftn.uns.ac.rs.unknowndevice.util.UnknownDeviceData;
import com.google.gson.Gson;

@Service
public class UnknownDeviceService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CertificateRepository certService;
	
	@Autowired
	private Environment env;
	
	@Async
    @Scheduled(fixedRate = 5000)
    public void sendBloodData() throws InterruptedException {
				
		Gson gson = new Gson();
		Random r = new Random();
				
		UnknownDeviceData deviceData = new UnknownDeviceData();
	
		//150-199
		deviceData.setPatientID(r.nextInt(200 - 150) + 150);
		//35.0-42.5  
		deviceData.setTemperature(42.5 + (r.nextDouble() * 35.0));
		
		String data = gson.toJson(deviceData);
		
		byte[] compressed_data = EncryptionUtil.compress(data);
		
		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), 
				certService.getMyPrivateKey(), compressed_data, this.env.getProperty("cipherKey"));
		
		ResponseEntity<HttpStatus> responseEntity = 
				restTemplate.postForEntity("https://localhost:8081/device/temperature-device", finalMess, HttpStatus.class);
		
		System.out.println(responseEntity.getStatusCode());
    }
}
