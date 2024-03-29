package com.ftn.uns.ac.rs.hospitalapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;

@Repository
public interface BloodDataRepository extends JpaRepository<BloodData, Long> {

	@Query(
			value = "SELECT * FROM blood_data ORDER BY date desc",
			nativeQuery = true
			)
	public Page<BloodData> findAllByDate(Pageable pageable);
	
	@Query(
			value = "SELECT * FROM blood_data WHERE patient_id = :patientID ORDER BY date desc",
			nativeQuery = true
			)
	public Page<BloodData> findAllByPatient(Pageable pageable, @Param("patientID") long patientID);
	
}
