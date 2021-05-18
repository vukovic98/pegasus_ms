package com.ftn.uns.ac.rs.heartmonitor.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.heartmonitor.repository.CertificateRepository;
import com.ftn.uns.ac.rs.heartmonitor.util.EncryptionUtil;
import com.ftn.uns.ac.rs.heartmonitor.util.FinalMessage;
import com.ftn.uns.ac.rs.heartmonitor.util.HeartMonitorData;
import com.google.gson.Gson;

@Service
public class HeartMonitorService {
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
				
		HeartMonitorData heartMonitorData = new HeartMonitorData();
		//30-150
		heartMonitorData.setHeartRate(r.nextInt(151 - 30) + 30);
		//150-199
		heartMonitorData.setPatientID(r.nextInt(200 - 150) + 150);
		//60-100
		heartMonitorData.setSaturation(r.nextInt(101 - 60) + 60);
		//80-196
		int systolic = r.nextInt(196-80)+80;
		//50-130
		int diastolic = r.nextInt(131-50)+50;
		heartMonitorData.setBloodPressure(systolic + "/" + diastolic);
		
		String data = gson.toJson(heartMonitorData);
		
		byte[] compressed_data = EncryptionUtil.compress(data);
		
		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), 
				certService.getMyPrivateKey(), compressed_data, this.env.getProperty("cipherKey"));
		
		ResponseEntity<HttpStatus> responseEntity = 
				restTemplate.postForEntity("https://localhost:8081/device/heart-monitor", finalMess, HttpStatus.class);
		
		System.out.println(responseEntity.getStatusCode());
    }
}
