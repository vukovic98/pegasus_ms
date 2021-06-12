package com.ftn.uns.ac.rs.hospitalapp.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	@Query(value = "SELECT * FROM alarm WHERE date >= ?1", nativeQuery = true)
	public ArrayList<Alarm> findByStartDate(Date startDate);
	
	@Query(value = "SELECT * FROM alarm WHERE date <= ?1", nativeQuery = true)
	public ArrayList<Alarm> findByEndDate(Date endDate);
	
	@Query(value = "SELECT * FROM alarm WHERE date >= ?1 AND date <= ?2", nativeQuery = true)
	public ArrayList<Alarm> findByDate(Date startDate, Date endDate);
	
}
