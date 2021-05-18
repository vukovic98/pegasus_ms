package com.ftn.uns.ac.rs.adminapp.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
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

import com.ftn.uns.ac.rs.adminapp.beans.Log;
import com.ftn.uns.ac.rs.adminapp.beans.LoginAttempt;
import com.ftn.uns.ac.rs.adminapp.beans.LoginStatus;
import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.adminapp.dto.UserTokenStateDTO;
import com.ftn.uns.ac.rs.adminapp.repository.LogRepository;
import com.ftn.uns.ac.rs.adminapp.security.TokenUtils;
import com.ftn.uns.ac.rs.adminapp.service.CustomUserDetailsService;
import com.ftn.uns.ac.rs.adminapp.service.LoginAttemptService;
import com.ftn.uns.ac.rs.adminapp.service.UserService;
import com.ftn.uns.ac.rs.adminapp.util.CipherEncrypt;
import com.ftn.uns.ac.rs.adminapp.util.LoggerProxy;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@SuppressWarnings("unused")
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

	@PostMapping(path = "/test")
	public ResponseEntity<HttpStatus> test(@RequestBody String mess) {
		System.out.println(mess);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/log-in")
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(
			@Valid @RequestBody LoginDTO authenticationRequest, HttpServletResponse response) {

		try {
			boolean verified = true;

			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			User user = (User) authentication.getPrincipal();

			String email = user.getEmail();
			String jwt = tokenUtils.generateToken(user.getEmail());
			int expiresIn = tokenUtils.getExpiredIn();

			this.loginAttemptService
					.save(new LoginAttempt(null, authenticationRequest.getEmail(), LoginStatus.SUCCESS, new Date()));

			this.logger.info("Successfull login attempt was made from [ " + authenticationRequest.getEmail() + " ]",
					AuthenticationController.class);

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

					this.logger.error("[USER IS BLOCKED] Failed login attempt was made from [ "
							+ authenticationRequest.getEmail() + " ]", AuthenticationController.class);

					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				} else {
					if (u.isEnabled()) {

						this.logger.error("[INCORRECT PASSWORD] Failed login attempt was made from [ "
								+ authenticationRequest.getEmail() + " ]", AuthenticationController.class);

						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					} else {

						this.logger.error("[USER IS BLOCKED] Failed login attempt was made from [ "
								+ authenticationRequest.getEmail() + " ]", AuthenticationController.class);

						return new ResponseEntity<>(HttpStatus.FORBIDDEN);
					}
				}
			} else {

				this.logger.warn("[UNKNOWN MAIL ADDRESS] Failed login attempt was made from [ "
						+ authenticationRequest.getEmail() + " ]", AuthenticationController.class);

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

			String content = "You have successfully enabled your account. \nGo to the login page now! \n\n http://localhost:4200/login";
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
