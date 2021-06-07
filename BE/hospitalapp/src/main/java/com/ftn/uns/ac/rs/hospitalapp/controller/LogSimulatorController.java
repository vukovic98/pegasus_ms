package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.util.Date;

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

import com.ftn.uns.ac.rs.hospitalapp.beans.LoginAttempt;
import com.ftn.uns.ac.rs.hospitalapp.beans.LoginStatus;
import com.ftn.uns.ac.rs.hospitalapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.hospitalapp.events.RequestEvent;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxyLogSimulator;
import com.ftn.uns.ac.rs.hospitalapp.security.XSSFilter;
import com.ftn.uns.ac.rs.hospitalapp.service.CertificateService;
import com.ftn.uns.ac.rs.hospitalapp.service.KieStatefulSessionService;
import com.ftn.uns.ac.rs.hospitalapp.service.LoginAttemptService;
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

	@PostMapping(path = "/log-in-sim-good")
	public ResponseEntity<HttpStatus> logInSimulationGood(@RequestBody FinalMessage finalMess) {

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

	}

	@PostMapping(path = "/log-in-sim-wrong-password")
	public ResponseEntity<HttpStatus> logInSimulationWrongPassword(@RequestBody FinalMessage finalMess) {

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

		}

		loginAttemptService.delete(dto.getEmail());
		
		return ResponseEntity.ok().body(HttpStatus.OK);

	}
	
	@GetMapping(path = "/request-sim-dos")
	public ResponseEntity<HttpStatus> requestSimulationDoS() {

		KieSession session = kieService.getEventsSession();
		session.getAgenda().getAgendaGroup("requests").setFocus();
		DoSAlarm dosAlarm = new DoSAlarm();
		session.setGlobal("dosAlarm", dosAlarm);
		
		for (int i = 0; i < 52; i++) {

			RequestEvent event = new RequestEvent(new Date(), "");
			session.insert(event);

		}
		
		session.fireAllRules();

        if(dosAlarm.isUnderAttack()) {
        	
			this.logger.warn("[DoS ATTACK DETECTED] The service is under a DoS attack - "
					+ "more than 50 requests detected in the span of 1 minute. ", XSSFilter.class, "LOG_SIMULATOR");
        			
        }
		
        kieService.disposeEventsSession();
        
		return ResponseEntity.ok().body(HttpStatus.OK);

	}

}
