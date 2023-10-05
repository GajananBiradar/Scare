package com.scare.service;

import java.util.List;

import com.scare.payloads.PriceMastersServiceDto;

public interface PriceMasterSService {

	// To get Price Master Service by id
	PriceMastersServiceDto getPriceMasterServiceById(String priceMasterServiceId);

	// To get all Price Master Services Using Pagination and Filter
	List<PriceMastersServiceDto> getPriceMasterServicesByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir);

	// To get all Price Master Services
	List<PriceMastersServiceDto> getAllPriceMasterServices();

	// Get Price Master Services by options
	List<PriceMastersServiceDto> getPriceMasterServiceByOptions(String option);

	// Add Price Master Service
	PriceMastersServiceDto createPriceMasterService(PriceMastersServiceDto priceMasterServiceDto);

	// Update Price Master Service
	PriceMastersServiceDto updatePriceMasterService(PriceMastersServiceDto priceMasterServiceDto);

	// Delete Price Master Service
	void deletePriceMasterService(String priceMasterServiceId);
	
}
