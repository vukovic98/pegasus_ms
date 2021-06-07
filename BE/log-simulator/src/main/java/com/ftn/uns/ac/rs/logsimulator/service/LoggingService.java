package com.ftn.uns.ac.rs.logsimulator.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ftn.uns.ac.rs.logsimulator.dto.LoginDTO;
import com.ftn.uns.ac.rs.logsimulator.repository.CertificateRepository;
import com.ftn.uns.ac.rs.logsimulator.util.EncryptionUtil;
import com.ftn.uns.ac.rs.logsimulator.util.FinalMessage;
import com.ftn.uns.ac.rs.logsimulator.util.SimulatorState;
import com.google.gson.Gson;

@Service
public class LoggingService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CertificateRepository certService;
	
	@Autowired
	private Environment env;
	
	private final SimulatorState INITIAL_STATE = SimulatorState.NORMAL_SIGN_IN_ATTEMPT;
	private SimulatorState CURRENT_STATE = INITIAL_STATE;
	
	private Gson gson = new Gson();
	private Random r = new Random();
	
	@Async
    @Scheduled(fixedRate = 5000)
    public void generateLogs() throws InterruptedException {
				
		LoginDTO dto = new LoginDTO();
		
		switch(CURRENT_STATE) {
		
		case NORMAL_SIGN_IN_ATTEMPT:
			dto.setEmail("vlado.hospital@mailinator.com");
			dto.setPassword("vukovic");
			
			signIn(dto, "https://localhost:8081/logsim/log-in-sim-good" );
			
			CURRENT_STATE = SimulatorState.ATTACK_SIGN_IN_ATTEMPT;
			
			break;
		case ATTACK_SIGN_IN_ATTEMPT:
			
			dto.setEmail("some.email@mailinator.com");
			dto.setPassword("vukovic");
			
			signIn(dto, "https://localhost:8081/logsim/log-in-sim-wrong-password");
			
			CURRENT_STATE = SimulatorState.ATTACK_REQUEST_AMOUNT;
			
			break;
	
		case ATTACK_REQUEST_AMOUNT:
			
			dosAttack();
			
			CURRENT_STATE = SimulatorState.NORMAL_SIGN_IN_ATTEMPT;
			
			break;
		
		default:
			System.out.println("UNIMPLEMENTED");
		
		}
		
    }
	
	private void signIn(LoginDTO dto, String path) {
	
		System.out.println(dto.getEmail());
		System.out.println(dto.getPassword());
		
		String data = gson.toJson(dto);
		
		byte[] compressed_data = EncryptionUtil.compress(data);
		
		FinalMessage finalMess = EncryptionUtil.encrypt(certService.getBobsPublicKey(), 
				certService.getMyPrivateKey(), compressed_data, this.env.getProperty("cipherKey"));
		
		ResponseEntity<HttpStatus> responseEntity = 
				restTemplate.postForEntity(path, finalMess, HttpStatus.class);
		
		System.out.println(responseEntity.getStatusCode());
		
	}

	private void dosAttack() {
		
		ResponseEntity<HttpStatus> responseEntity = 
				restTemplate.getForEntity("https://localhost:8081/logsim/request-sim-dos", HttpStatus.class);
		
		System.out.println(responseEntity.getStatusCode());
		
	}
	
	
}
