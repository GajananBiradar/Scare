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
import com.scare.exceptions.DuplicateIdException;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.payloads.GSTDto;
import com.scare.service.GSTService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/gst")
public class GSTController {

	private static final Logger logger = LoggerFactory.getLogger(GSTController.class);

	@Autowired
	GSTService gstService;

	/*
	 * Returns a GST based on entered/selected GST Id
	 * 
	 * @param {String} id the id of the GST for which details are required
	 * 
	 * @return {GSTDto} an object of all the details of the GST
	 */
	@GetMapping("/{gstId}")
	public ResponseEntity<GSTDto> getGSTById(@PathVariable("gstId") String gstId) {
		logger.info("GSTController: getGSTById - inputGstId ::: {}", gstId);

		if (this.gstService.getGSTById(gstId) == null) {
			throw new ResourceNotFoundException("GST", "id: ", Integer.parseInt(gstId));
		}
		try {
			GSTDto gstById = this.gstService.getGSTById(gstId);
			logger.info("GSTController: getGSTById - foundGstId ::: {}", gstId);

			return new ResponseEntity(gstById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("GSTController: getGSTById - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Returns list of GSTs
	 * 
	 * @return {Array<GSTDto>}
	 */
	@GetMapping("/")
	public ResponseEntity<List<GSTDto>> getAllGSTs() {
		logger.info("GSTController: getAllGSTs ");
		try {
			List<GSTDto> gstList = this.gstService.getAllGSTs();
			if (gstList.isEmpty()) {
				logger.error("GSTController: getAllGSTs - gstList.size() ::: {}", gstList.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(gstList, HttpStatus.OK);
		} catch (Exception ex) {
			// Log the error
			logger.error("GSTController: getAllGSTs - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Creates and enters into db a new GST
	 * 
	 * @param {GSTDto} object containing all details of the GST to be entered
	 * 
	 * @return {GSTDto} all entries of GST after successfully inserting into db
	 */
	@PostMapping("/")
	public ResponseEntity<GSTDto> createGST(@RequestBody GSTDto gstDto) {
		if (gstDto == null) {
			logger.info("GSTController: createGST - inputGstDto - GSTDto is null ::: {}", gstDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("GSTController: createGST - inputGstDto ::: {}", gstDto);
		try {
			GSTDto createGST = this.gstService.createGST(gstDto);
			return new ResponseEntity<>(createGST, HttpStatus.CREATED);
		} catch (DuplicateIdException ex) {
			logger.error("GSTController: createGST - Duplicate HSN code ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("GSTController: createGST - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Update a GST by ID
	 * 
	 * @param {GSTDto, gstId} object containing all the details about GST and id of
	 * GST
	 * 
	 * @return {GSTDto} returns updated GST
	 */
	@PutMapping("/")
	public ResponseEntity<GSTDto> updateGST(@Valid @RequestBody GSTDto gstDto) {
		logger.info("GSTController: updateGST - gstDto ::: {}", gstDto);

		GSTDto updatedGST = this.gstService.updateGST(gstDto);

		return new ResponseEntity<>(updatedGST, HttpStatus.OK);
	}

	/*
	 * Delete a GST by GSTId
	 * 
	 * @param {gstId} takes GST Id
	 * 
	 * @return {String} confirmation of successful deletion
	 */
	@DeleteMapping("/{gstId}")
	public ResponseEntity<ApiResponse> deleteGST(@PathVariable("gstId") String gstId) {
		logger.info("GSTController: deleteGST - gstId ::: {}", gstId);

		try {
			this.gstService.deleteGST(gstId);
			return ResponseEntity.ok(new ApiResponse("GST deleted successfully", true));
		} catch (ResourceNotFoundException ex) {
			logger.error("GSTController: deleteGST - inputGstId - error ::: GST not found: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("GSTController: deleteGST - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
