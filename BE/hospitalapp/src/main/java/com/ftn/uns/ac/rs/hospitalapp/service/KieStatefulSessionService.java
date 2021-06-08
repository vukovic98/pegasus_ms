package com.ftn.uns.ac.rs.hospitalapp.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class KieStatefulSessionService {

	@Autowired
	@Qualifier(value = "kieContainterSecurity")
	private KieContainer kieContainter;

	private KieSession eventsSession;

	public KieContainer getKieContainer() {
		return kieContainter;
	}

	public KieSession getEventsSession() {
		if (eventsSession == null) {
			eventsSession = kieContainter.newKieSession("eventSession");
		}
		return eventsSession;
	}
	
	public void disposeEventsSession() {
		
    	if (this.eventsSession != null) {
            this.eventsSession.dispose();
            this.eventsSession = null;
    	}
		
	}

}
