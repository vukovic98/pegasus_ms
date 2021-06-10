package com.ftn.uns.ac.rs.hospitalapp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.drools.template.ObjectDataCompiler;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.dto.LogTypeDTO;

@Service
public class SecurityDataService {

	public boolean createRuleForLogType(LogTypeDTO logType) {

		try {
			InputStream template = new FileInputStream(
					"..\\security-kjar\\src\\main\\resources\\sbnz\\templates\\status-template.drt");

			// Compile template to generate new rules
			List<LogTypeDTO> arguments = new ArrayList<>();
			arguments.add(logType);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\security-kjar\\src\\main\\resources\\sbnz\\events\\logs-"+logType.getLogType()+".drl"));
			drlFile.write(drl.getBytes());
			drlFile.close();

			// Update Rules project
			InvocationRequest request = new DefaultInvocationRequest();
			request.setPomFile(new File("../security-kjar/pom.xml"));
			request.setGoals(Arrays.asList("clean", "install"));

			Invoker invoker = new DefaultInvoker();
			invoker.setMavenHome(new File(System.getenv("M2_HOME")));
			invoker.execute(request);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
