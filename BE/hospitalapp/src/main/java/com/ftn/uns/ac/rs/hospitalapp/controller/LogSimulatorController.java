package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Doctor;
import com.ftn.uns.ac.rs.hospitalapp.beans.LoginAttempt;
import com.ftn.uns.ac.rs.hospitalapp.beans.LoginStatus;
import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;
import com.ftn.uns.ac.rs.hospitalapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.hospitalapp.events.RequestEvent;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxyLogSimulator;
import com.ftn.uns.ac.rs.hospitalapp.security.XSSFilter;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.service.KieStatefulSessionService;
import com.ftn.uns.ac.rs.hospitalapp.service.LoginAttemptService;
import com.ftn.uns.ac.rs.hospitalapp.service.SecurityKnowledgeService;
import com.ftn.uns.ac.rs.hospitalapp.service.UserService;
import com.ftn.uns.ac.rs.hospitalapp.util.CertificateRevokedException;
import com.ftn.uns.ac.rs.hospitalapp.util.DoSAlarm;
import com.ftn.uns.ac.rs.hospitalapp.util.EncryptionUtil;
import com.ftn.uns.ac.rs.hospitalapp.util.FinalMessage;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/logsim")
public class LogSimulatorController {

	@Autowired
	LoggerProxyLogSimulator logger;

	@Autowired
	private CertificateService certService;

	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@Autowired
	private KieStatefulSessionService kieService;
	
	@Autowired
	private SecurityKnowledgeService securityService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private UserService userService;

	@PostMapping(path = "/log-in-sim-good")
	public ResponseEntity<HttpStatus> logInSimulationGood(@RequestBody FinalMessage finalMess) {

		try {
		
		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getLogSimPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		System.out.println(data);

		LoginDTO dto = gson.fromJson(data, LoginDTO.class);

		System.out.println(dto.getEmail());
		System.out.println(dto.getPassword());

		this.logger.log("Successfull login attempt was made from [ " + dto.getEmail() + " ]",
				AuthenticationController.class, "LOG_SIMULATOR");

		return ResponseEntity.ok().body(HttpStatus.OK);
		
		}catch(CertificateRevokedException e) {
			
			System.out.println("Certificate revoked!");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
		}

	}

	@PostMapping(path = "/log-in-sim-wrong-password")
	public ResponseEntity<HttpStatus> logInSimulationWrongPassword(@RequestBody FinalMessage finalMess) {

		try {
		
		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getLogSimPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		System.out.println(data);

		LoginDTO dto = gson.fromJson(data, LoginDTO.class);

		System.out.println(dto.getEmail());
		System.out.println(dto.getPassword());

		for (int i = 0; i < 5; i++) {

			LoginAttempt attempt = new LoginAttempt(null, dto.getEmail(), LoginStatus.FAIL, new Date());
			loginAttemptService.save(attempt);

		}

		if (loginAttemptService.isUserForBlock(dto.getEmail())) {

			this.logger.error("[USER IS BLOCKED] Failed login attempt was made from [ " + dto.getEmail() + " ]",
					AuthenticationController.class, "LOG_SIMULATOR");

			SecurityAlarm a = new SecurityAlarm("LOG_SIMULATOR", "USER IS BLOCKED", new Date());
			
			this.securityService.save(a);
			this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
			
		}

		loginAttemptService.delete(dto.getEmail());
		
		return ResponseEntity.ok().body(HttpStatus.OK);
		
		}catch(CertificateRevokedException e) {
			
			System.out.println("Certificate revoked!");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
		}

	}
	
	@PostMapping(path = "/log-in-sim-malicious-ip")
	public ResponseEntity<HttpStatus> logInSimulationMaliciousIP(@RequestBody FinalMessage finalMess, HttpServletRequest request) throws IOException {

		try {
		
		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getLogSimPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		System.out.println(data);

		LoginDTO dto = gson.fromJson(data, LoginDTO.class);

		System.out.println(dto.getEmail());
		System.out.println(dto.getPassword());


		Path maliciousPath = Paths.get("../hospitalapp/src/main/resources/static/malicious_ip.txt");
		List<String> maliciousIPs = Files.readAllLines(maliciousPath);
		
		System.out.println(request.getRemoteAddr());
		
		if(maliciousIPs.contains(request.getRemoteAddr())) {
			
			this.logger.warn("[MALICIOUS IP RECOGNIZED] Login attempt was made from malicious IP address! [ "
			+request.getRemoteAddr()+" ]", AuthenticationController.class, "LOG_SIMULATOR");
			
			SecurityAlarm a = new SecurityAlarm("LOG_SIMULATOR", "MALICIOUS IP RECOGNIZED", new Date());
			
			this.securityService.save(a);
			this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
			
		}
		
		return ResponseEntity.ok().body(HttpStatus.OK);

		}catch(CertificateRevokedException e) {
			
			System.out.println("Certificate revoked!");
			return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
			
		}
	}
	
	@PostMapping(path = "/log-in-sim-inactive-account")
	public ResponseEntity<HttpStatus> logInSimulationInactiveAccount(@RequestBody FinalMessage finalMess, HttpServletRequest request) throws IOException {

		try {
		
		Gson gson = new Gson();

		byte[] compressedData = EncryptionUtil.decrypt(finalMess, certService.getLogSimPublicKey(),
				certService.getMyPrivateKey());

		String data = EncryptionUtil.decompress(compressedData);

		System.out.println(data);

		LoginDTO dto = gson.fromJson(data, LoginDTO.class);

		System.out.println(dto.getEmail());
		System.out.println(dto.getPassword());

		Doctor d = new Doctor();
		d.setLastActivityTime(Instant.now().minus(100, ChronoUnit.DAYS).toEpochMilli());
		
		if(userService.isUserInactiveForMonths(d)) {
			
			this.logger.warn("[INACTIVE USER DETECTED] Login attempt after over 90 days of inactivity was made from [ "
			+dto.getEmail()+" ]", AuthenticationController.class, "LOG_SIMULATOR");
			
			SecurityAlarm a = new SecurityAlarm("LOG_SIMULATOR", "INACTIVE USER DETECTED", new Date());
			
			this.securityService.save(a);
			this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
			
		}
		
		return ResponseEntity.ok().body(HttpStatus.OK);

		}catch(CertificateRevokedException e) {
			
			System.out.println("Certificate revoked!");
			return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
			
		}
		
	}
	
	@GetMapping(path = "/request-sim-dos")
	public ResponseEntity<HttpStatus> requestSimulationDoS() {

		KieSession session = kieService.getEventsSession();
		session.getAgenda().getAgendaGroup("requests").setFocus();
		DoSAlarm dosAlarm = new DoSAlarm();
		session.setGlobal("dosAlarm", dosAlarm);
		
		ArrayList<SecurityAlarm> alarms = new ArrayList<>();
		
		session.setGlobal("alarms", alarms);
		
		for (int i = 0; i < 52; i++) {

			RequestEvent event = new RequestEvent(new Date(), "");
			session.insert(event);

		}
		
		session.fireAllRules();

        if(dosAlarm.isUnderAttack()) {
        	
			this.logger.warn("[DoS ATTACK DETECTED] The service is under a DoS attack - "
					+ "more than 50 requests detected in the span of 1 minute. ", XSSFilter.class, "LOG_SIMULATOR");
        			
			for (SecurityAlarm a : alarms) {
				a.setIpAddress("LOG_SIMULATOR");
				this.securityService.save(a);
				this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
			}
			
        }
		
        kieService.disposeEventsSession();
        
		return ResponseEntity.ok().body(HttpStatus.OK);

	}

}
