package com.scare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scare.model.Category;

//import org.springframework.data.mongodb.repository.MongoRepository;

import com.scare.model.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

//	Product findById(int productId);
	
//	List<Product> findByUser_Id(int seller_id);
	
	List<Product> findByCategory(Category category);

}
