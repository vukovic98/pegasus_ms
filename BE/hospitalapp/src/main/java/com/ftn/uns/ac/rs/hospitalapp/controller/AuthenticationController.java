package com.ftn.uns.ac.rs.hospitalapp.controller;

import java.security.cert.X509Certificate;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.ftn.uns.ac.rs.hospitalapp.beans.LoginAttempt;
import com.ftn.uns.ac.rs.hospitalapp.beans.LoginStatus;
import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.UserTokenStateDTO;
import com.ftn.uns.ac.rs.hospitalapp.security.TokenUtils;
import com.ftn.uns.ac.rs.hospitalapp.service.CustomUserDetailsService;
import com.ftn.uns.ac.rs.hospitalapp.service.LoginAttemptService;
import com.ftn.uns.ac.rs.hospitalapp.service.UserService;
import com.ftn.uns.ac.rs.hospitalapp.util.CipherEncrypt;

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
	
	@PostMapping(path = "/test")
	public ResponseEntity<HttpStatus> test(@RequestBody String mess, HttpServletRequest req) {
		System.out.println(mess);
		
//		X509Certificate[] certs = (X509Certificate[])req.getAttribute("javax.servlet.request.X509Certificate");

//		System.out.println(certs.length);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/log-in")
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@Valid @RequestBody LoginDTO authenticationRequest,
			HttpServletResponse response) {

		try {
			boolean verified = true;
			System.out.println("dodje");
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));

			// Ubaci korisnika u trenutni security kontekst
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Kreiraj token za tog korisnika
			User user = (User) authentication.getPrincipal();

			String email = user.getEmail();
			String jwt = tokenUtils.generateToken(user.getEmail()); // prijavljujemo se na sistem sa email adresom
			int expiresIn = tokenUtils.getExpiredIn();

			this.loginAttemptService
					.save(new LoginAttempt(null, authenticationRequest.getEmail(), LoginStatus.SUCCESS, new Date()));

			// Vrati token kao odgovor na uspesnu autentifikaciju
			return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn, email, verified));
		} catch (Exception e) {
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
					}

					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				} else {
					if (u.isEnabled())
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					else
						return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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

			return new ResponseEntity<>(content, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
