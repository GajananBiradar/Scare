package com.scare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scare.model.Category;


import com.scare.model.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE p.product_name = ?1")
	Product findByProduct_name(String product_name);	
	
	List<Product> findByCategory(Category category);

}
