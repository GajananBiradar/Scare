package com.scare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scare.exceptions.ApiResponse;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.payloads.ServiceTypeDto;
import com.scare.payloads.ProductDto;
import com.scare.service.ServiceTypeService;
import com.scare.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/serviceType")
public class ServiceTypeController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTypeController.class);

	@Autowired
	private ServiceTypeService serviceTypeService;

	@GetMapping("/h/hello")
	public String hello() {
		System.out.println("hello hello");
		return "Hello, world!";
	}

	 /*
     * Returns a ServiceType based on entered/selected ServiceType Id
     * 
     * @param {String} id the id of the ServiceType for which details are required
     * 
     * @return {ServiceTypeDto} an object of all the details of the ServiceType
     */
	@GetMapping("/{serviceTypeId}")
	public ResponseEntity<ServiceTypeDto> getServiceTypeById(@PathVariable("serviceTypeId") String serviceTypeId) {
		logger.info("ServiceTypeController: getServiceTypeId - inputServiceTypeId ::: {}", serviceTypeId);

		if (this.serviceTypeService.getServiceTypeById(serviceTypeId) == null) {
			throw new ResourceNotFoundException("ServiceType", "id: ", Integer.parseInt(serviceTypeId));
		}
		try {
			ServiceTypeDto serviceTypeById = this.serviceTypeService.getServiceTypeById(serviceTypeId);
			logger.info("ServiceTypeController: getServiceTypeId - foundServiceTypeId ::: {}", serviceTypeId);

			return new ResponseEntity(serviceTypeById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			logger.error("ProductController: getProductId - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	 /*
     * Returns list of ServiceTypes
     * 
     * @return {List<ServiceTypeDto>}
     */
	@GetMapping("/")
	public ResponseEntity<List<ServiceTypeDto>> getAllServiceTypes() {
		logger.info("ServiceTypeController: getAllServiceTypes ");
		try {
			List<ServiceTypeDto> serviceTypes = this.serviceTypeService.getAllServiceTypes();
			if (serviceTypes.isEmpty()) {
				logger.error("ServiceTypeController: getAllServiceTypes - serviceTypes.size() ::: {}",
						serviceTypes.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(serviceTypes, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("ServiceTypeController: getAllProducts - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	 /*
     * Creates and enters into DB a new ServiceType
     * 
     * @param {ServiceTypeDto} object containing all details of the ServiceType to be entered
     * 
     * @return {ServiceTypeDto} all entries of ServiceType after successfully inserting into DB
     */
	@PostMapping("/")
	public ResponseEntity<ServiceTypeDto> createServiceType(@RequestBody ServiceTypeDto serviceTypeDto) {
		if (serviceTypeDto == null) {
			logger.info(
					"ServiceTypeController: createServiceType - inputServiceTypeDto - ServiceTypeDto is null ::: {}",
					serviceTypeDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("ServiceTypeController: createServiceType - inputServiceTypeDto ::: {}", serviceTypeDto);
		try {
			ServiceTypeDto createServiceType = this.serviceTypeService.createServiceType(serviceTypeDto);
			return new ResponseEntity<>(createServiceType, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("ServiceTypeController: createServiceType - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	 /*
     * Update a ServiceType by ID
     * 
     * @param {ServiceTypeDto} object containing all the details about ServiceType and id of ServiceType
     * 
     * @return {ServiceTypeDto} returns updated ServiceType
     */
	@PutMapping("/")
	public ResponseEntity<ServiceTypeDto> updateServiceType(@Valid @RequestBody ServiceTypeDto serviceTypeDto) {
		logger.info("ServiceTypeController: updateServiceType - ServiceTypeDto, catId ::: {}", serviceTypeDto);

		ServiceTypeDto updatedServiceType = this.serviceTypeService.updateServiceType(serviceTypeDto);

		return new ResponseEntity<>(updatedServiceType, HttpStatus.OK);
	}

	/*
     * Delete a ServiceType by serviceTypeId
     * 
     * @param {serviceTypeId} takes serviceType Id
     * 
     * @return {ApiResponse} confirmation of successful deletion
     */
	@DeleteMapping("/{serviceTypeId}")
	public ResponseEntity<ApiResponse> deleteServiceType(@PathVariable("serviceTypeId") String serviceTypeId) {
		logger.info("ServiceTypeController: deleteServiceType - serviceTypeId ::: {}", serviceTypeId);

		try {
			this.serviceTypeService.deleteServiceType(serviceTypeId);
			return ResponseEntity.ok(new ApiResponse("ServiceType deleted successfully", true));
		} catch (ResourceNotFoundException ex) {
			logger.error(
					"ServiceTypeController: deleteServiceType - inputServiceTypeId - error ::: ServiceType not found: {}",
					ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			logger.error("ServiceTypeController: deleteServiceType - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
