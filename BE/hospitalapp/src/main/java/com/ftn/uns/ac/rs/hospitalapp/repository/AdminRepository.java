package com.ftn.uns.ac.rs.hospitalapp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	@Query(value = "SELECT * FROM pegasus_hospital.admin WHERE hospital_id=?1", nativeQuery = true)
	public ArrayList<Admin> findByHospitalId(long id);
}
