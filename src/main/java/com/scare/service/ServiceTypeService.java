package com.scare.service;

import java.util.List;

import com.scare.payloads.CategoryDto;
import com.scare.payloads.ServiceTypeDto;

public interface ServiceTypeService {

	// To get ServiceType by id
	ServiceTypeDto getServiceTypeById(String serviceTypeId);

	// To get all ServiceTypes Using Pagination
	List<ServiceTypeDto> getServiceTypeByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir);

	// To get all ServiceType
	List<ServiceTypeDto> getAllServiceTypes();

	// Get ServiceType by options
	List<ServiceTypeDto> getServiceTypeByOptions(String option);

	// Add ServiceType
	ServiceTypeDto createServiceType(ServiceTypeDto serviceTypeDto);

	// Update ServiceType
	ServiceTypeDto updateServiceType(ServiceTypeDto serviceTypeDto);

	// Delete ServiceType
	void deleteServiceType(String serviceTypeId);
}
