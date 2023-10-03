package com.scare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scare.model.GST;

public interface GSTRepo extends JpaRepository<GST, String> {

	@Query(value= "SELECT * FROM Gst where hsn_code= ?1", nativeQuery = true)
	GST findByHsn_code(Long hsn_code);


}
