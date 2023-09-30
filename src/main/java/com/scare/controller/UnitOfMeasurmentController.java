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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scare.config.AppConstants;
import com.scare.exceptions.ApiResponse;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.UnitOfMeasurmentDto;
import com.scare.service.UnitOfMeasurmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/unitOfMeasurment")
public class UnitOfMeasurmentController {

	private static final Logger logger = LoggerFactory.getLogger(UnitOfMeasurmentController.class);

	@Autowired
	UnitOfMeasurmentService unitOfMeasurmentService;

	/*
	 * Returns a UnitOfMeasurment based on entered/selected UnitOfMeasurment Id
	 * 
	 * @param {String} id the id of the UnitOfMeasurment for which details are
	 * required
	 * 
	 * @return {UnitOfMeasurmentDto} an object of all the details of the
	 * UnitOfMeasurment
	 */
	@GetMapping("/{unitOfMeasurmentId}")
	public ResponseEntity<UnitOfMeasurmentDto> getUnitOfMeasurmentById(
			@PathVariable("unitOfMeasurmentId") String unitOfMeasurmentId) {
		logger.info("UnitOfMeasurmentController: getUnitOfMeasurmentId - inputUnitOfMeasurmentId ::: {}",
				unitOfMeasurmentId);

		if (this.unitOfMeasurmentService.getUnitOfMeasurmentById(unitOfMeasurmentId) == null) {
			throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(unitOfMeasurmentId));
		}
		try {
			UnitOfMeasurmentDto unitOfMeasurmentById = this.unitOfMeasurmentService
					.getUnitOfMeasurmentById(unitOfMeasurmentId);
			logger.info("UnitOfMeasurmentController: getUnitOfMeasurmentId - foundUnitOfMeasurmentId ::: {}",
					unitOfMeasurmentId);

			return new ResponseEntity(unitOfMeasurmentById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("UnitOfMeasurmentController: getUnitOfMeasurmentId - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Returns list of UnitOfMeasurments
	 * 
	 * @return {Array<UnitOfMeasurmentDto>}
	 */
	@GetMapping("/")
	public ResponseEntity<List<UnitOfMeasurmentDto>> getAllUnitOfMeasurments() {
		logger.info("UnitOfMeasurmentController: getAllUnitOfMeasurments ");
		try {
			List<UnitOfMeasurmentDto> unitOfMeasurments = this.unitOfMeasurmentService.getAllUnitOfMeasurments();
			if (unitOfMeasurments.isEmpty()) {
				logger.error("UnitOfMeasurmentController: getAllUnitOfMeasurments - categories.size() ::: {}",
						unitOfMeasurments.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(unitOfMeasurments, HttpStatus.OK);
		} catch (Exception ex) {
			// Log the error
			logger.error("UnitOfMeasurmentController: getAllUnitOfMeasurments - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Creates and enters into db a new UnitOfMeasurment
	 * 
	 * @param{pageNumber, pageSize, sortBy, sortDir} object containing all details
	 * of the UnitOfMeasurment to be entered
	 * 
	 * @return{List<UnitOfMeasurmentDto>} all entries of UnitOfMeasurment after
	 * successfully inserting into db
	 */
	@GetMapping("/getByPaginationAndFilter")
	private ResponseEntity<List<UnitOfMeasurmentDto>> getAllProducts() {
		return null;
	}

	/*
	 * Creates and enters into db a new UnitOfMeasurment
	 * 
	 * @param{UnitOfMeasurmentDto} object containing all details of the
	 * UnitOfMeasurment to be entered
	 * 
	 * @return{UnitOfMeasurmentDto} all entries of UnitOfMeasurment after
	 * successfully inserting into db
	 */
	@PostMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<UnitOfMeasurmentDto> createUnitOfMeasurment(
			@RequestBody UnitOfMeasurmentDto unitOfMeasurmentDto) {
		if (unitOfMeasurmentDto == null) {
			logger.info(
					"UnitOfMeasurmentController: createUnitOfMeasurment - inputUnitOfMeasurmentDto - UnitOfMeasurmentDto is null ::: {}",
					unitOfMeasurmentDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("UnitOfMeasurmentController: createUnitOfMeasurment - inputUnitOfMeasurmentDto ::: {}",
				unitOfMeasurmentDto);
		try {
			UnitOfMeasurmentDto createUnitOfMeasurment = this.unitOfMeasurmentService
					.createUnitOfMeasurment(unitOfMeasurmentDto);
			return new ResponseEntity<>(createUnitOfMeasurment, HttpStatus.CREATED);
		} catch (Exception ex) {
			// Log the error
			logger.error("UnitOfMeasurmentController: createUnitOfMeasurment - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Update a UnitOfMeasurment by ID
	 * 
	 * @param{UnitOfMeasurmentDto, unitOfMeasurmentId} object containing all the
	 * details about UnitOfMeasurment and id of UnitOfMeasurment
	 * 
	 * @return{UnitOfMeasurmentDto} returns updated UnitOfMeasurment
	 */
	@PutMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<UnitOfMeasurmentDto> updateUnitOfMeasurment(
			@Valid @RequestBody UnitOfMeasurmentDto unitOfMeasurmentDto) {
		logger.info("UnitOfMeasurmentController: updateUnitOfMeasurment - unitOfMeasurmentDto ::: {}",
				unitOfMeasurmentDto);

		UnitOfMeasurmentDto updatedUnitOfMeasurment = this.unitOfMeasurmentService
				.updateUnitOfMeasurment(unitOfMeasurmentDto);

		return new ResponseEntity<>(updatedUnitOfMeasurment, HttpStatus.OK);
	}

	/*
	 * Delete a category by UnitOfMeasurmentId
	 * 
	 * @param{unitOfMeasurmentId} takes UnitOfMeasurment Id
	 * 
	 * @return{String} confirmation of successful deletion
	 */
	@DeleteMapping("/{unitOfMeasurmentId}")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<ApiResponse> deleteUnitOfMeasurment(
			@PathVariable("unitOfMeasurmentId") String unitOfMeasurmentId) {
		logger.info("UnitOfMeasurmentController: deleteUnitOfMeasurment - catId ::: {}", unitOfMeasurmentId);

		try {
			this.unitOfMeasurmentService.deleteUnitOfMeasurment(unitOfMeasurmentId);
			return ResponseEntity.ok(new ApiResponse("UnitOfMeasurment deleted successfully", true));
		} catch (ResourceNotFoundException ex) {
			logger.error(
					"UnitOfMeasurmentController: deleteUnitOfMeasurment - inputUnitOfMeasurmentId - error ::: UnitOfMeasurment not found: {}",
					ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("UnitOfMeasurmentController: deleteUnitOfMeasurment - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
