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
import com.scare.payloads.PriceMastersServiceDto;
import com.scare.payloads.ProductDto;
import com.scare.service.PriceMasterSService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/priceMastersService")
public class PriceMastersServiceController {

	private static final Logger logger = LoggerFactory.getLogger(PriceMastersServiceController.class);

	@Autowired
	PriceMasterSService priceMasterSService;

	/*
	 * Returns a PriceMastersService based on entered/selected PriceMastersService
	 * Id
	 * 
	 * @param {String} id the id of the PriceMastersService for which details are
	 * required
	 * 
	 * @return {PriceMastersServiceDto} an object of all the details of the
	 * PriceMastersService
	 */
	@GetMapping("/{priceMastersServiceId}")
	public ResponseEntity<PriceMastersServiceDto> getPriceMastersServiceById(
			@PathVariable("priceMastersServiceId") String priceMastersServiceId) {
		logger.info("PriceMastersServiceController: getPriceMastersServiceId - inputPriceMastersServiceId ::: {}",
				priceMastersServiceId);

		if (this.priceMasterSService.getPriceMasterServiceById(priceMastersServiceId) == null) {
			throw new ResourceNotFoundException("PriceMastersService", "id: ", Integer.parseInt(priceMastersServiceId));
		}
		try {
			PriceMastersServiceDto productById = this.priceMasterSService
					.getPriceMasterServiceById(priceMastersServiceId);
			logger.info("PriceMastersServiceController: getPriceMastersServiceId - foundPriceMastersServiceId ::: {}",
					priceMastersServiceId);

			return new ResponseEntity(productById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("PriceMastersServiceController: getPriceMastersServiceId - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
	/*
	 * Returns list of PriceMasters
	 *
	 * @return {Array<PriceMastersDto>}
	 */
	@GetMapping("/")
	public ResponseEntity<List<PriceMastersServiceDto>> getAllPriceMasters() {
		logger.info("PriceMastersServiceController: getAllPriceMastersService ");
		try {
			List<PriceMastersServiceDto> priceMastersService = this.priceMasterSService.getAllPriceMasterServices();
			if (priceMastersService.isEmpty()) {
				logger.error(
						"PriceMastersServiceController: getAllPriceMastersService - PriceMastersService.size() ::: {}",
						priceMastersService.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(priceMastersService, HttpStatus.OK);
		} catch (Exception ex) {
			// Log the error
			logger.error("PriceMastersServiceController: getAllPriceMastersService - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<PriceMastersServiceDto> createPriceMastersService(
			@RequestBody PriceMastersServiceDto priceMastersServiceDto) {
		if (priceMastersServiceDto == null) {
			logger.info(
					"PriceMastersServiceController: createPriceMastersService - inputPriceMastersServiceDto - PriceMastersServiceDto is null ::: {}",
					priceMastersServiceDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("PriceMastersServiceController: createPriceMastersService - inputPriceMastersServiceDto ::: {}",
				priceMastersServiceDto);
		try {
			PriceMastersServiceDto createPriceMastersService = this.priceMasterSService
					.createPriceMasterService(priceMastersServiceDto);
			return new ResponseEntity<>(createPriceMastersService, HttpStatus.CREATED);
		} catch (Exception ex) {
			// Log the error
			logger.error("PriceMastersServiceController: createPriceMastersService - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Update a priceMastersService by ID
	 *
	 * @param {PriceMastersServiceIdDto} object containing all the details about
	 * PriceMastersService and id of PriceMastersService
	 *
	 * @return {PriceMastersServiceIdDto} returns updated PriceMastersService
	 */
	@PutMapping("/")
	public ResponseEntity<PriceMastersServiceDto> updatePriceMasters(
			@Valid @RequestBody PriceMastersServiceDto priceMastersServiceDto) {
		logger.info("PriceMastersServiceController: updatePriceMastersService - PriceMastersServiceDto, catId ::: {}",
				priceMastersServiceDto);

		PriceMastersServiceDto updatedPriceMasters = this.priceMasterSService
				.updatePriceMasterService(priceMastersServiceDto);

		return new ResponseEntity<>(updatedPriceMasters, HttpStatus.OK);
	}

	/*
	 * Delete a PriceMasters by priceMastersId
	 *
	 * @param {priceMastersId} takes priceMasters Id
	 *
	 * @return {String} confirmation of successful deletion
	 */
	@DeleteMapping("/{priceMastersId}")
	public ResponseEntity<ApiResponse> deletePriceMasters(
			@PathVariable("priceMastersId") String priceMastersServiceId) {
		logger.info("PriceMastersServiceController: deletePriceMastersService - PriceMastersServiceId ::: {}",
				priceMastersServiceId);

		try {
			this.priceMasterSService.deletePriceMasterService(priceMastersServiceId);
			return ResponseEntity.ok(new ApiResponse("PriceMastersService deleted successfully", true));
		} catch (ResourceNotFoundException ex) {
			logger.error(
					"PriceMastersServiceController: deletePriceMastersService - inputPriceMastersServiceId - error ::: PriceMastersService not found: {}",
					ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("PriceMastersServiceController: deletePriceMastersService - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
