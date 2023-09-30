package com.scare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.scare.model.Category;

public interface CategoryRepo extends JpaRepository<Category, String>{

//	Category findById(int catId);
	
	@Query("SELECT c FROM Category c WHERE c.category_name = ?1")
	Category findByCategory_name(String category_name);	
	
}
