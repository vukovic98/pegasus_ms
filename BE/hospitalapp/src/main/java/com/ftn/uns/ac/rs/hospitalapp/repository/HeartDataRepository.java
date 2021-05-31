package com.ftn.uns.ac.rs.hospitalapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.util.HeartMonitorData;

@Repository
public interface HeartDataRepository extends JpaRepository<HeartMonitorData, Long> {

	@Query(
			value = "SELECT * FROM heart_data ORDER BY date desc",
			nativeQuery = true
			)
	public Page<HeartMonitorData> findAllByDate(Pageable pageable);
	
}
