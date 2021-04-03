package com.ftn.uns.ac.rs.hospitalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	
}
