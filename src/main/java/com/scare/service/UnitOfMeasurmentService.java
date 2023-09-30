package com.scare.service;

import java.util.List;

import com.scare.payloads.ProductDto;
import com.scare.payloads.UnitOfMeasurmentDto;

public interface UnitOfMeasurmentService {

	// To get UnitOfMeasurment by id
	UnitOfMeasurmentDto getUnitOfMeasurmentById(String UnitOfMeasurmentId);

	// To get all UnitOfMeasurments Using Pagination and Filter
	List<UnitOfMeasurmentDto> getUnitOfMeasurmentsByPaginationAndFilter(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir);

	// To get all UnitOfMeasurments
	List<UnitOfMeasurmentDto> getAllUnitOfMeasurments();

	// Get UnitOfMeasurment by options
	List<UnitOfMeasurmentDto> getUnitOfMeasurmentByOptions(String option);

	// Add UnitOfMeasurment
	UnitOfMeasurmentDto createUnitOfMeasurment(UnitOfMeasurmentDto unitOfMeasurmentDto);

	// Update UnitOfMeasurment
	UnitOfMeasurmentDto updateUnitOfMeasurment(UnitOfMeasurmentDto unitOfMeasurmentDto);

	// Delete UnitOfMeasurment
	void deleteUnitOfMeasurment(String unitOfMeasurmentId);
}
