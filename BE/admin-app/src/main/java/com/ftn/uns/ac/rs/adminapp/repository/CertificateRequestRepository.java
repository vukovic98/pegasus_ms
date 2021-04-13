package com.ftn.uns.ac.rs.adminapp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;

@Repository
public interface CertificateRequestRepository extends JpaRepository<CertificateRequest, Long> {

	public CertificateRequest findOneById(long id);
	
	@Query(
			value = "SELECT * FROM CERTIFICATE_REQUEST WHERE verified = true",
			nativeQuery = true
			)
	public ArrayList<CertificateRequest> findAllVerified();
	
	@Query(
			value = "SELECT * FROM CERTIFICATE_REQUEST WHERE verified = false",
			nativeQuery = true
			)
	public ArrayList<CertificateRequest> findAllUnverified();
}
