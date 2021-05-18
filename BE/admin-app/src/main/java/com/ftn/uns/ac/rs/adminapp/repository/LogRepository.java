package com.ftn.uns.ac.rs.adminapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.adminapp.beans.Log;

@Repository
public interface LogRepository extends MongoRepository<Log, Long> {

}
