package com.ftn.uns.ac.rs.hospitalapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.ac.rs.hospitalapp.beans.DeviceLog;

@Repository
public interface DeviceLogRepository extends MongoRepository<DeviceLog, Long> {

}
