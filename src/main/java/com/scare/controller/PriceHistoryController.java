package com.scare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scare.exceptions.ResourceNotFoundException;
import com.scare.model.PriceHistory;
import com.scare.payloads.PriceMastersServiceDto;
import com.scare.repository.PriceHistoryRepo;

@RestController
@RequestMapping("/api/master/priceHistoryService")
public class PriceHistoryController {

	private static final Logger logger = LoggerFactory.getLogger(PriceHistoryController.class);

	@Autowired
	PriceHistoryRepo priceHistoryRepo;
	
	/*
	 * Returns a PriceHistoryService based on entered/selected PriceHistoryService
	 * Id
	 * 
	 * @param {String} id the id of the PriceHistoryService for which details are
	 * required
	 * 
	 * @return {PriceHistoryService} an object of all the details of the
	 * PriceHistoryService
	 */
	@GetMapping("/{priceHistoryServiceId}")
	public ResponseEntity<?> getPriceHistoryServiceById(
			@PathVariable("priceHistoryServiceId") String priceHistoryServiceId) {
		logger.info("PriceHistoryServiceController: getPriceHistoryServiceId - inputPriceHistoryServiceId ::: {}",
				priceHistoryServiceId);

		if (this.priceHistoryRepo.findByPriceHistoryServiceId(priceHistoryServiceId) == null) {
			throw new ResourceNotFoundException("PriceHistoryService", "id: ", Integer.parseInt(priceHistoryServiceId));
		}
		try {
			List<PriceHistory> priceHistorysServiceId = this.priceHistoryRepo.findByPriceHistoryServiceId(priceHistoryServiceId);
			logger.info("priceHistoryServiceController: getPriceHistoryServiceById - foundpriceHistorysServiceId ::: {}",
					priceHistorysServiceId);

			return new ResponseEntity(priceHistorysServiceId, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("PriceHistoryServiceController: getPriceHistoryServiceById - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
