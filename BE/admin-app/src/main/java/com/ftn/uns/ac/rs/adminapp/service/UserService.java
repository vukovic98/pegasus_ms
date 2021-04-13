package com.ftn.uns.ac.rs.adminapp.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.adminapp.beans.User;
import com.ftn.uns.ac.rs.adminapp.dto.LoginDTO;
import com.ftn.uns.ac.rs.adminapp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

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

	public String createMailBody(String mail) {

		StringBuffer sb = new StringBuffer();

		sb.append("<code>Hello, <br><br>");
		sb.append("We are sorry for the inconvenience. We detected some suspicious activities from your account.");
		sb.append("You tried to login with wrong password more than 5 times in the period of 5 minutes.<br>");
		sb.append("If this was you, please click on the following link in order to enable your account.<br><br>");
		sb.append("<h2>http://localhost:8080/auth/enable-account/" + mail + "</h2><br><br>");
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

	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	public void save(User u) {
		this.userRepository.save(u);
	}
}
