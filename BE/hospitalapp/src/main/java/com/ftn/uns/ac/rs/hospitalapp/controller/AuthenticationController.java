package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.beans.LoginAttempt;
import com.ftn.uns.ac.rs.hospitalapp.beans.LoginStatus;
import com.ftn.uns.ac.rs.hospitalapp.beans.Patient;
import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;
import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.dto.LogTypeDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.UserTokenStateDTO;
import com.ftn.uns.ac.rs.hospitalapp.events.FailedLoginEvent;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.security.TokenUtils;
import com.ftn.uns.ac.rs.hospitalapp.service.CustomUserDetailsService;
import com.ftn.uns.ac.rs.hospitalapp.service.KieStatefulSessionService;
import com.ftn.uns.ac.rs.hospitalapp.service.LoginAttemptService;
import com.ftn.uns.ac.rs.hospitalapp.service.SecurityDataService;
import com.ftn.uns.ac.rs.hospitalapp.service.SecurityKnowledgeService;
import com.ftn.uns.ac.rs.hospitalapp.service.UserService;
import com.ftn.uns.ac.rs.hospitalapp.util.CipherEncrypt;
import com.ftn.uns.ac.rs.hospitalapp.util.LoginAlarm;


@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private Environment env;
	
	@Autowired
	private LoggerProxy logger;
	
	@Autowired
	private SecurityKnowledgeService securityService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	@PostMapping(path = "/test-test")
	public ResponseEntity<HttpStatus> test(@RequestBody String s) {
		System.out.println(s);
		
//		X509Certificate[] certs = (X509Certificate[])req.getAttribute("javax.servlet.request.X509Certificate");

//		System.out.println(certs.length);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/log-in")
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@Valid @RequestBody LoginDTO authenticationRequest,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			boolean verified = true;
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));

			// Ubaci korisnika u trenutni security kontekst
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Kreiraj token za tog korisnika
			User user = (User) authentication.getPrincipal();

			
			
			String email = user.getEmail();
			String jwt = tokenUtils.generateToken(user.getEmail()); // prijavljujemo se na sistem sa email adresom
			int expiresIn = tokenUtils.getExpiredIn();

			if(userService.isUserInactiveForMonths(user)) {
				
				this.logger.warn("[INACTIVE USER DETECTED] Login attempt after over 90 days of inactivity was made from [ "
				+authenticationRequest.getEmail()+" ]", AuthenticationController.class);
				
				SecurityAlarm a = new SecurityAlarm(request.getRemoteAddr(), "INACTIVE USER DETECTED", new Date());
				
				this.securityService.save(a);
				this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
				
			}
			
			Path maliciousPath = Paths.get("../hospitalapp/src/main/resources/static/malicious_ip.txt");
			List<String> maliciousIPs = Files.readAllLines(maliciousPath);
			
			if(maliciousIPs.contains(request.getRemoteAddr())) {
				
				this.logger.warn("[MALICIOUS IP RECOGNIZED] Login attempt was made from malicious IP address! [ "
				+request.getRemoteAddr()+" ]", AuthenticationController.class);
				
				SecurityAlarm a = new SecurityAlarm(request.getRemoteAddr(), "MALICIOUS IP RECOGNIZED", new Date());
				
				this.securityService.save(a);
				this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
				
			}
			
			this.loginAttemptService
					.save(new LoginAttempt(null, authenticationRequest.getEmail(), LoginStatus.SUCCESS, new Date()));

			this.logger.info("Successfull login attempt was made from [ " + authenticationRequest.getEmail() + " ]",
					AuthenticationController.class);
			
			System.out.println(request.getRemoteAddr());
			
			user.setLastActivityTime(Instant.now().toEpochMilli());
			userService.save(user);
			
			// Vrati token kao odgovor na uspesnu autentifikaciju
			return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn, email, verified));
		} catch (Exception e) {
			
			
			ArrayList<SecurityAlarm> alarms = securityService.maliciousIP(request.getRemoteAddr());
			
			for (SecurityAlarm a : alarms) {
				this.securityService.save(a);
				this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
			}
			
			e.printStackTrace();
			User u = this.userService.findByEmail(authenticationRequest.getEmail());

			if (u != null) {
				this.loginAttemptService
						.save(new LoginAttempt(null, authenticationRequest.getEmail(), LoginStatus.FAIL, new Date()));

				boolean notOK = this.loginAttemptService.isUserForBlock(authenticationRequest.getEmail());

				if (notOK) {
					if (u.isEnabled()) {
						u.setEnabled(false);
						this.userService.save(u);

						this.userService.sendMailToBlockedUser(u.getEmail());
						
						SecurityAlarm a = new SecurityAlarm("", "USER IS BLOCKED", new Date());
						
						this.securityService.save(a);
						this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
						
					}

					this.logger.error("[USER IS BLOCKED] Failed login attempt was made from [ "
							+ authenticationRequest.getEmail() + " ]", AuthenticationController.class);
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				} else {
					if (u.isEnabled()) {
						
						this.logger.error("[INCORRECT PASSWORD] Failed login attempt was made from [ "
								+ authenticationRequest.getEmail() + " ]", AuthenticationController.class);
						
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
						
					}
					else {
						this.logger.error("[USER IS BLOCKED] Failed login attempt was made from [ "
								+ authenticationRequest.getEmail() + " ]", AuthenticationController.class);
						
						return new ResponseEntity<>(HttpStatus.FORBIDDEN);
						
					}
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

	}

	@GetMapping(path = "/enable-account/{mail}")
	public ResponseEntity<String> enableAccount(@PathVariable("mail") String mail) {

		String email = CipherEncrypt.decrypt(mail, env.getProperty("cipherKey"));

		User u = this.userService.findByEmail(email);

		if (u != null) {
			u.setEnabled(true);
			this.userService.save(u);

			String content = "You have successfully enabled your account. \nGo to the login page now! \n\n https://localhost:4200/login";
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.TEXT_HTML);
			
			this.logger.info("Successfull enabling account attempt for user [ " + mail + " ]",
					AuthenticationController.class);

			return new ResponseEntity<>(content, HttpStatus.OK);
		} else {
			
			this.logger.warn("[UNKNOWN MAIL ADDRESS] Failed enabling account attempt for user [ " + mail + " ]",
					AuthenticationController.class);
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
