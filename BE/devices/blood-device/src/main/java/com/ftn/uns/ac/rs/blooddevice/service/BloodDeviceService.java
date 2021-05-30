package com.ftn.uns.ac.rs.blooddevice.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.blooddevice.repository.CertificateRepository;
import com.ftn.uns.ac.rs.blooddevice.util.BloodData;
import com.ftn.uns.ac.rs.blooddevice.util.EncryptionUtil;
import com.ftn.uns.ac.rs.blooddevice.util.FinalMessage;
import com.google.gson.Gson;

@Service
public class BloodDeviceService {

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
				
		BloodData bloodData = new BloodData();
		
		bloodData.setPatientID(r.nextInt(50) + 150);
		bloodData.setCRP(0 + (r.nextDouble() * 20)); //0 - 100 (OK 2 - 5)
		bloodData.setErythrocytes(3 + (r.nextDouble() * 4)); // 3 - 7 (OK 4 - 6)
		bloodData.setHemoglobin(11.5 + (r.nextDouble() * 8)); // 11.5 - 19.5 (OK 13.5 - 17.5)
		bloodData.setLeukocytes(3.5 + (r.nextDouble() * 9)); // 3.5 - 12.5 (OK 4.5 - 11)
		
		String data = gson.toJson(bloodData);
		
		byte[] compressed_data = EncryptionUtil.compress(data);
		
		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), 
				certService.getMyPrivateKey(), compressed_data, this.env.getProperty("cipherKey"));
		
		ResponseEntity<HttpStatus> responseEntity = 
				restTemplate.postForEntity("https://localhost:8081/device/blood-device", finalMess, HttpStatus.class);
		
		System.out.println(responseEntity.getStatusCode());
    }
	
}
