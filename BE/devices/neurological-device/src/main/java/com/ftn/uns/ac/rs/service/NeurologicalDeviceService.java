package com.ftn.uns.ac.rs.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.repository.CertificateRepository;
import com.ftn.uns.ac.rs.util.EncryptionUtil;
import com.ftn.uns.ac.rs.util.FinalMessage;
import com.ftn.uns.ac.rs.util.NeurologicalData;
import com.google.gson.Gson;

@Service
public class NeurologicalDeviceService {

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
				
		NeurologicalData neuroData = new NeurologicalData();

		neuroData.setPatientId(r.nextInt(50) + 150);
		
		neuroData.setICP(1.5 + (r.nextDouble()) * 49);
		neuroData.setBIS(0 + (r.nextDouble() * 100));
		
		String data = gson.toJson(neuroData);
		
		byte[] compressed_data = EncryptionUtil.compress(data);
		
		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), 
				certService.getMyPrivateKey(), compressed_data, this.env.getProperty("cipherKey"));
		
		ResponseEntity<HttpStatus> responseEntity = 
				restTemplate.postForEntity("https://localhost:8081/device/neurological-device", finalMess, HttpStatus.class);
		
		System.out.println(responseEntity.getStatusCode());
    }
	
}
