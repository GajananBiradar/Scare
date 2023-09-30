package com.scare.service;

import java.util.List;

import com.scare.payloads.BrandDto;
import com.scare.payloads.CategoryDto;

public interface BrandService {
	
	// To get Brand by id
	BrandDto getBrandById(String brandId);

	// To get all Brands Using Pagination and Filter
	List<BrandDto> getBrandByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir);

	// To get all Brands
	List<BrandDto> getAllBrands();

	// Get Brand by options
	List<BrandDto> getBrandByOptions(String option);

	// create Brand
	BrandDto createBrand(BrandDto brandDto);

	// Update Brand
	BrandDto updateBrand(BrandDto brandDto);

	// Delete Product
	void deleteBrand(String brandId);
}
