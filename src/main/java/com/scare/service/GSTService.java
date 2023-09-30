package com.scare.service;

import java.util.List;

import com.scare.payloads.CategoryDto;
import com.scare.payloads.GSTDto;

public interface GSTService {

	// To get GST by id
	GSTDto getGSTById(String gstId);

	// To get all GSTs Using Pagination
	List<GSTDto> getGSTByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//To get all GST
	List<GSTDto> getAllGSTs();

	//Get GST by options
	List<GSTDto> getGSTByOptions(String option);
	
	// Add GST
	GSTDto createGST(GSTDto gstDto);

	// Update GST
	GSTDto updateGST(GSTDto gstDto);

	// Delete GST
	void deleteGST(String gstId);
	
}
