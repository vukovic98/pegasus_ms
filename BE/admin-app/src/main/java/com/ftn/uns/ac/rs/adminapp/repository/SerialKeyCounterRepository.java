package com.ftn.uns.ac.rs.adminapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.adminapp.beans.SerialKeyCounter;

@Repository
public interface SerialKeyCounterRepository extends JpaRepository<SerialKeyCounter, Long> {

}
