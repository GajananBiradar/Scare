package com.scare.service;

import java.util.List;

import com.scare.payloads.ProductDto;

public interface ProductService {

	// To get product by id
	ProductDto getProductById(String productId);

	// To get all Products Using Pagination and Filter
	List<ProductDto> getProductsByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//To get all Products
	List<ProductDto> getAllProducts();

	//Get product by options
	List<ProductDto> getProductByOptions(String option);
	
	// Add Product
	ProductDto createProduct(ProductDto productDto);

	// Update Product
	ProductDto updateProduct(ProductDto productDto);

	// Delete Product
	void deleteProduct(String productId);

}
