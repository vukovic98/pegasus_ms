package com.ftn.uns.ac.rs.hospitalapp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.drools.template.ObjectDataCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeDTO;
import com.ftn.uns.ac.rs.hospitalapp.repository.NeurologicalDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.NeurologicalData;

@Service
public class NeurologicalDataService {

	@Autowired
	private NeurologicalDataRepository neurologicalDataRepository;
	
	public NeurologicalData insert(NeurologicalData d) {
		d.setDate(new Date());
		return this.neurologicalDataRepository.save(d);
	}
	
	public Page<NeurologicalData> findAll(Pageable pageable) {
		return this.neurologicalDataRepository.findAllByDate(pageable);
	}
	
	public Page<NeurologicalData> findAllByPatientId(Pageable pageable, long id){
		return this.neurologicalDataRepository.findAllByPatient(pageable, id);
	}
	
	public boolean createRuleForHeartmonitorBIS(DataRangeDTO dto) {

		try {
			InputStream template = new FileInputStream(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\templates\\neurological-data-BIS.drt");

			// Compile template to generate new rules
			List<DataRangeDTO> arguments = new ArrayList<>();
			arguments.add(dto);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\neurological-data-BIS-"+dto.getPatientID()+".drl"));
			drlFile.write(drl.getBytes());
			drlFile.close();

			// Update Rules project
			InvocationRequest request = new DefaultInvocationRequest();
			request.setPomFile(new File("../devices-kjar/pom.xml"));
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
	
	public boolean createRuleForHeartmonitorICP(DataRangeDTO dto) {

		try {
			InputStream template = new FileInputStream(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\templates\\neurological-data-ICP.drt");

			// Compile template to generate new rules
			List<DataRangeDTO> arguments = new ArrayList<>();
			arguments.add(dto);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\neurological-data-ICP-"+dto.getPatientID()+".drl"));
			drlFile.write(drl.getBytes());
			drlFile.close();

			// Update Rules project
			InvocationRequest request = new DefaultInvocationRequest();
			request.setPomFile(new File("../devices-kjar/pom.xml"));
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
