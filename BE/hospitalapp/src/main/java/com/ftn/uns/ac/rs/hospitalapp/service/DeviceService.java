package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.ArrayList;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;

@Service
public class DeviceService {

	@Autowired
	private KieContainer kContainer;
	
	public ArrayList<Alarm> bloodData(BloodData d) {
		
		KieSession kieSession = kContainer.newKieSession();
		
		ArrayList<Alarm> alarms = new ArrayList<>();
		
		kieSession.setGlobal("alarms", alarms);

		kieSession.insert(d);
		kieSession.getAgenda().getAgendaGroup("blood-data").setFocus();
		kieSession.fireAllRules();

		kieSession.dispose();
				
		return alarms;
	}
}
