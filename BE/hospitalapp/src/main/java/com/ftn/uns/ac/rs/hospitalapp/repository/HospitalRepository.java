package com.ftn.uns.ac.rs.hospitalapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

	public Optional<Hospital> findById(long id);
	
}
