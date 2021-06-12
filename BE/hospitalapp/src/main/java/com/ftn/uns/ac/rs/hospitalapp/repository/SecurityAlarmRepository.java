package com.ftn.uns.ac.rs.hospitalapp.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;

@Repository
public interface SecurityAlarmRepository extends JpaRepository<SecurityAlarm, Long>{

	@Query(value = "SELECT * FROM security_alarm WHERE date >= ?1", nativeQuery = true)
	public ArrayList<SecurityAlarm> findByStartDate(Date startDate);
	
	@Query(value = "SELECT * FROM security_alarm WHERE date <= ?1", nativeQuery = true)
	public ArrayList<SecurityAlarm> findByEndDate(Date endDate);
	
	@Query(value = "SELECT * FROM security_alarm WHERE date >= ?1 AND date <= ?2", nativeQuery = true)
	public ArrayList<SecurityAlarm> findByDate(Date startDate, Date endDate);
}
