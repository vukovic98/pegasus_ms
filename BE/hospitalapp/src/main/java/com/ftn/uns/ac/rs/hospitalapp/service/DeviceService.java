package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.ArrayList;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;

@Service
public class DeviceService {

	@Autowired
	private KieContainer kieContainer;
	
	public ArrayList<Alarm> bloodData(BloodData d) {
		KieSession kieSession = kieContainer.newKieSession();
		
		kieSession.insert(d);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		return new ArrayList<>();
	}
	
	public ArrayList<Alarm> neurologicalData(NeurologicalData data){
		KieSession kieSession = kieContainer.newKieSession();
		
		ArrayList<Alarm> alarms = new ArrayList<>();
		
		kieSession.setGlobal("alarms", alarms);
		
		
		kieSession.insert(data);
		kieSession.getAgenda().getAgendaGroup("neuro-data").setFocus();
		kieSession.fireAllRules();
		
		System.out.println(alarms.size());
		
		kieSession.dispose();
		
		
		
		return alarms;
		
	}
}
