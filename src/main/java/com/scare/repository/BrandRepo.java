package com.scare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scare.model.Brand;
import com.scare.model.Category;

public interface BrandRepo extends JpaRepository<Brand,String>{

	@Query("SELECT b FROM Brand b WHERE b.brand_name = ?1")
	Brand findByBrand_name(String brand_name);	
	
}
