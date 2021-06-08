package com.ftn.uns.ac.rs.hospitalapp.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.User;
import com.ftn.uns.ac.rs.hospitalapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.hospitalapp.repository.UserRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.CipherEncrypt;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	public boolean deleteUserById(long id) {
		try {
			this.userRepository.deleteById(id);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public User findById(long id) {
		return this.userRepository.findById(id).orElse(null);
	}

	public String createMailBody(String mail) {

		StringBuffer sb = new StringBuffer();

		String cipherMail = CipherEncrypt.encrypt(mail, env.getProperty("cipherKey"));

		sb.append("<code>Hello, <br><br>");
		sb.append("We are sorry for the inconvenience. We detected some suspicious activities from your account.");
		sb.append("You tried to login with wrong password more than 5 times in the period of 5 minutes.<br>");
		sb.append("If this was you, please click on the following link in order to enable your account.<br><br>");
		sb.append("<h2>https://localhost:8081/auth/enable-account/" + cipherMail + "</h2><br><br>");
		sb.append("Sincerely,<br> Pegasus MS Team</code>");

		return sb.toString();
	}

	@Async
	public void sendMailToBlockedUser(String email) {
		try {
			MimeMessage msg = this.javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);

			helper.setTo(email);
			helper.setSubject("Pegasus Medical System : Verification Mail");

			helper.setText(createMailBody(email), true);
			this.javaMailSender.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(User u) {
		this.userRepository.save(u);
	}

	public User login(LoginDTO dto) {
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(dto.getEmail());

		if (matcher.matches()) {
			User found = this.userRepository.findByEmail(dto.getEmail());

			if (found != null) {
				BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

				if (enc.matches(dto.getPassword(), found.getPassword()))
					return found;
				else
					return null;
			} else
				return null;
		} else {
			return null;
		}
	}
	
	public boolean isUserInactiveForMonths(User user) {
		
		Instant currTime = Instant.now();
		
		Instant lastLogin = Instant.ofEpochMilli(user.getLastActivityTime());
		
		if(lastLogin.plus(90, ChronoUnit.DAYS).isBefore(currTime)) {
			
			return true;
		}else {
			return false;
		}
		
		
	}

}
