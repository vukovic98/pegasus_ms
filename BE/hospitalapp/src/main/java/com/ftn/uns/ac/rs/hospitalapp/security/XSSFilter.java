package com.ftn.uns.ac.rs.hospitalapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;
import com.ftn.uns.ac.rs.hospitalapp.events.RequestEvent;
import com.ftn.uns.ac.rs.hospitalapp.mongo.proxy.LoggerProxy;
import com.ftn.uns.ac.rs.hospitalapp.service.KieStatefulSessionService;
import com.ftn.uns.ac.rs.hospitalapp.service.SecurityKnowledgeService;
import com.ftn.uns.ac.rs.hospitalapp.util.DoSAlarm;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSFilter implements Filter {
	
	@Autowired
	private LoggerProxy logger;
	
	@Autowired
	private KieStatefulSessionService kieService;
	
	@Autowired
	private SecurityKnowledgeService securityService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

    	System.out.println("XSS FILTER");
    	
        XSSRequestWrapper wrappedRequest = new XSSRequestWrapper((HttpServletRequest) request);

        String body = IOUtils.toString(wrappedRequest.getReader());
        if (!StringUtils.isBlank(body)) {
            body = XSSUtils.stripXSS(body);
            wrappedRequest.resetInputStream(body.getBytes());
        }

        KieSession eventSession = kieService.getEventsSession();
		eventSession.getAgenda().getAgendaGroup("requests").setFocus();
		DoSAlarm dosAlarm = new DoSAlarm();
		eventSession.setGlobal("dosAlarm", dosAlarm);
        
		ArrayList<SecurityAlarm> alarms = new ArrayList<>();
		
		eventSession.setGlobal("alarms", alarms);
		
        RequestEvent event = new RequestEvent(new Date(), request.getRemoteAddr());
        
        eventSession.insert(event);
        eventSession.fireAllRules();
        
        System.out.println(dosAlarm.isUnderAttack());
        
        if(dosAlarm.isUnderAttack()) {
        	
			this.logger.warn("[DoS ATTACK DETECTED] The service is under a DoS attack - "
					+ "more than 50 requests detected in the span of 1 minute. ", XSSFilter.class);
        	
			for (SecurityAlarm a : alarms) {
				this.securityService.save(a);
				this.simpMessagingTemplate.convertAndSend("/log-alarm", a);
			}
			
        }
        
        chain.doFilter(wrappedRequest, response);
    }

}
