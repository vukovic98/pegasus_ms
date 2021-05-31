package com.ftn.uns.ac.rs.hospitalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.util.BloodData;

@Repository
public interface BloodDataRepository extends JpaRepository<BloodData, Long> {

}
