package com.ftn.uns.ac.rs.hospitalapp.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.LoginAttempt;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

	@Query(
			value = "SELECT * FROM pegasus_hospital.login_attempt where email= :email and timestamp >= :date order by timestamp desc",
			nativeQuery = true)
	public ArrayList<LoginAttempt> findRecentLogins(@Param("email") String email, @Param("date") Date date);
	
	@Modifying
	@Query(
			value = "DELETE FROM pegasus_hospital.login_attempt WHERE email = ?1",
			nativeQuery = true
			)
	public void deleteByEmail(String email);
	
}
