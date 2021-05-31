package com.ftn.uns.ac.rs.hospitalapp.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.repository.AlarmRepository;

@Service
public class AlarmService {
	
	@Autowired
	private AlarmRepository alarmRepository;
	
	public Page<Alarm> findAll(Pageable pageable){
		return (Page<Alarm>) this.alarmRepository.findAll(pageable);
	}
	

}
