package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.LoginAttempt;
import com.ftn.uns.ac.rs.hospitalapp.beans.LoginStatus;
import com.ftn.uns.ac.rs.hospitalapp.repository.LoginAttemptRepository;

@Service
public class LoginAttemptService {

	@Autowired
	private LoginAttemptRepository loginAttemptRepository;
	
	public boolean isUserForBlock(String email) {
		
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.MINUTE, -5);
		
		ArrayList<LoginAttempt> atm = this.loginAttemptRepository.findRecentLogins(email, c.getTime());
		
		int cnt = 0;
		
		for(LoginAttempt a : atm) {
			if(a.getStatus().equals(LoginStatus.FAIL))
				cnt++;
			
			if(cnt >= 5)
				return true;
			
			if(a.getStatus().equals(LoginStatus.SUCCESS))
				break;
		}
		
		return false;
	}
	
	public void save(LoginAttempt a) {
		this.loginAttemptRepository.save(a);
	}
	
	@Transactional
	public void delete(String email) {
		this.loginAttemptRepository.deleteByEmail(email);
	}
}
