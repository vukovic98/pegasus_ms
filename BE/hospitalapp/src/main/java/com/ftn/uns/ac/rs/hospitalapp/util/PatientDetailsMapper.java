package com.ftn.uns.ac.rs.hospitalapp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.uns.ac.rs.hospitalapp.beans.Patient;
import com.ftn.uns.ac.rs.hospitalapp.dto.PatientDTO;

@Component
public class PatientDetailsMapper {
	
	public PatientDTO entityToDto(Patient patient) {
		PatientDTO patientDto = new PatientDTO();
		
		patientDto.setAverageBloodPressure(patient.getAverageBloodPressure());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
		patientDto.setBirthday(sdf.format(patient.getBirthday()));
		patientDto.setCurrentAge(patient.getCurrentAge());
		patientDto.setGender(patient.getGender());
		patientDto.setHeight(patient.getHeight());
		patientDto.setWeight(patient.getWeight());
		patientDto.setName(patient.getFirstName() + " " + patient.getLastName());
		patientDto.setId(patient.getId());
		patientDto.setPastMedicalRecord(patient.getPastMedicalHistory());
		
		return patientDto;
	}
	
	public List<PatientDTO> entityListToDtoList(List<Patient> patients){
		List<PatientDTO> patientsDto = new ArrayList<PatientDTO>();
		
		for(Patient p : patients) {
			patientsDto.add(entityToDto(p));
		}
		
		return patientsDto;
	}

}
