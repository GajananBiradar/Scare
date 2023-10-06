package com.scare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.mongodb.repository.MongoRepository;

import com.scare.model.PostalPincode;

public interface PostalPincodeRepo extends JpaRepository<PostalPincode, Long>{

	 PostalPincode  findByArea(String area);
}
