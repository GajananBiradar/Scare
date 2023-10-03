package com.scare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scare.model.ServiceType;

public interface ServiceTypeRepo extends JpaRepository<ServiceType, String>{

}
