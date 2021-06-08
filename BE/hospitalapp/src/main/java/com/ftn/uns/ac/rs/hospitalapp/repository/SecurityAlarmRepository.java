package com.ftn.uns.ac.rs.hospitalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.SecurityAlarm;

@Repository
public interface SecurityAlarmRepository extends JpaRepository<SecurityAlarm, Long>{

}
