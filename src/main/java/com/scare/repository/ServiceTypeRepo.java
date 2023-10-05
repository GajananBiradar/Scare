package com.scare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scare.model.ServiceType;

public interface ServiceTypeRepo extends JpaRepository<ServiceType, String>{

	@Query("SELECT s FROM ServiceType s WHERE s.serviceType = ?1")
	ServiceType findByService_Type(String serviceType);
	
}
