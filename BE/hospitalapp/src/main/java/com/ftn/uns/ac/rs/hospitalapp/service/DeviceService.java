package com.ftn.uns.ac.rs.hospitalapp.service;

import java.util.ArrayList;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;
import com.ftn.uns.ac.rs.hospitalapp.repository.AlarmRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;
import com.ftn.uns.ac.rs.hospitalapp.util.HeartMonitorData;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;

@Service
public class DeviceService {

	@Autowired
	private KieContainer kContainer;
	
	@Autowired
	private AlarmRepository alarmRepository;
	
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
	
	public ArrayList<Alarm> neurologicalData(NeurologicalData data){
		KieSession kieSession = kContainer.newKieSession();
		
		ArrayList<Alarm> alarms = new ArrayList<>();
		
		kieSession.setGlobal("alarms", alarms);
		
		
		kieSession.insert(data);
		kieSession.getAgenda().getAgendaGroup("neuro-data").setFocus();
		kieSession.fireAllRules();
		
		System.out.println(alarms.size());
		
		kieSession.dispose();
		
		
		
		return alarms;
		
	}
	
	public ArrayList<Alarm> heartMonitorData(HeartMonitorData data){
		KieSession kieSession = kContainer.newKieSession();
		
		ArrayList<Alarm> alarms = new ArrayList<>();
		
		kieSession.setGlobal("alarms", alarms);
		
		
		kieSession.insert(data);
		kieSession.getAgenda().getAgendaGroup("heartmonitor").setFocus();
		kieSession.fireAllRules();
		
		System.out.println(alarms.size());
		
		kieSession.dispose();
		
		
		
		return alarms;
		
	}

	public Alarm save(Alarm a) {
		return this.alarmRepository.save(a);
	}
}
