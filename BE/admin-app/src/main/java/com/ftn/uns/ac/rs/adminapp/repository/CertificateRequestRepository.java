package com.ftn.uns.ac.rs.adminapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.adminapp.beans.CertificateRequest;

@Repository
public interface CertificateRequestRepository extends JpaRepository<CertificateRequest, Long> {

	public CertificateRequest findOneById(long id);
}
