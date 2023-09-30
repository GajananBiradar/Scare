package com.scare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scare.model.Brand;

public interface BrandRepo extends JpaRepository<Brand,String>{

}
