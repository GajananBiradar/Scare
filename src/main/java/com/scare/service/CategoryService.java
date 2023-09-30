package com.scare.service;

import java.util.List;

import com.scare.payloads.CategoryDto;
import com.scare.payloads.ProductDto;

public interface CategoryService {

	// To get Category by id
	CategoryDto getCategoryById(String categoryId);

	// To get all Category's Using Pagination
	List<CategoryDto> getCategoryByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//To get all Category
	List<CategoryDto> getAllCategories();

	//Get Category by options
	List<CategoryDto> getCategoryByOptions(String option);
	
	// Add Category
	CategoryDto createCategory(CategoryDto categoryDto);

	// Update Category
	CategoryDto updateCategory(CategoryDto categoryDto);

	// Delete Category
	void deleteCategory(String categoryId);
}
