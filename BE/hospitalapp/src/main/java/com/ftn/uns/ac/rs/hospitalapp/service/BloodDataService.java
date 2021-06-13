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

import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeCombinedDTO;
import com.ftn.uns.ac.rs.hospitalapp.dto.DataRangeDTO;
import com.ftn.uns.ac.rs.hospitalapp.repository.BloodDataRepository;
import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;

@Service
public class BloodDataService {

	@Autowired
	private BloodDataRepository bloodDataRepository;
	
	public BloodData insert(BloodData d) {
		d.setDate(new Date());
		return this.bloodDataRepository.save(d);
	}
	
	public Page<BloodData> findAllByPatient(Pageable pageable, long id) {
		return this.bloodDataRepository.findAllByPatient(pageable, id);
	}
	
	public Page<BloodData> findAll(Pageable pageable) {
		return this.bloodDataRepository.findAllByDate(pageable);
	}
	
	public boolean createRuleForCRP(DataRangeDTO dataRange) {
		try {
			InputStream template = new FileInputStream(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\templates\\blood-data-CRP.drt");

			// Compile template to generate new rules
			List<DataRangeDTO> arguments = new ArrayList<>();
			arguments.add(dataRange);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\blood-data-CRP-" + dataRange.patientID + ".drl"));
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

	public boolean createRuleForErythrocites(DataRangeDTO dataRange) {
		try {
			InputStream template = new FileInputStream(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\templates\\blood-data-erythrocytes.drt");

			// Compile template to generate new rules
			List<DataRangeDTO> arguments = new ArrayList<>();
			arguments.add(dataRange);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\blood-data-erythrocytes-" + dataRange.patientID + ".drl"));
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
	
	public boolean createRuleForHemoglobin(DataRangeDTO dataRange) {
		try {
			InputStream template = new FileInputStream(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\templates\\blood-data-hemoglobin.drt");

			// Compile template to generate new rules
			List<DataRangeDTO> arguments = new ArrayList<>();
			arguments.add(dataRange);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\blood-data-hemoglobin-" + dataRange.patientID + ".drl"));
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
	
	public boolean createRuleForLeukocytes(DataRangeDTO dataRange) {
		try {
			InputStream template = new FileInputStream(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\templates\\blood-data-leukocytes.drt");

			// Compile template to generate new rules
			List<DataRangeDTO> arguments = new ArrayList<>();
			arguments.add(dataRange);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\blood-data-leukocytes-" + dataRange.patientID + ".drl"));
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

	public boolean createRuleForBloodDeviceCombined(DataRangeCombinedDTO dto) {
		try {
			InputStream template = new FileInputStream(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\templates\\blood_data_combined.drt");

			// Compile template to generate new rules
			List<DataRangeCombinedDTO> arguments = new ArrayList<>();
			arguments.add(dto);
			ObjectDataCompiler compiler = new ObjectDataCompiler();
			String drl = compiler.compile(arguments, template);

			// Save rule to drl file
			FileOutputStream drlFile = new FileOutputStream(new File(
					"..\\devices-kjar\\src\\main\\resources\\sbnz\\integracija\\blood_data_"+dto.getAttrName1()+"_"+dto.getAttrName2()+"_"+dto.getPatientID()+".drl"));
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
