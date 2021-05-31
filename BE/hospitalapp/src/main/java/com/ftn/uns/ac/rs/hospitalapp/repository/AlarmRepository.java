package com.ftn.uns.ac.rs.hospitalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.uns.ac.rs.hospitalapp.beans.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
