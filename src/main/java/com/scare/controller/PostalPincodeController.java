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
import com.scare.model.PostalPincode;
import com.scare.payloads.BrandDto;
import com.scare.payloads.PostalPincodeDto;
import com.scare.payloads.PriceMastersServiceDto;
import com.scare.service.BrandService;
import com.scare.service.PostalPincodeService;
import com.scare.service.impl.PostalPincodeServiceImpl;

@RestController
@RequestMapping("/api/master/postalPincode")
public class PostalPincodeController {

	private static final Logger logger = LoggerFactory.getLogger(PostalPincodeController.class);

	@Autowired
	private PostalPincodeService postalPincodeService;

	@GetMapping("/h/hello")
	public String hello() {
		System.out.println("hello hello");
		return "Hello, world!";
	}
	
	/*
	 * Returns a Postal Pincode based on entered/selected Brand Id
	 * 
	 * @param {long} pincode the pincode of the PostalPincode for which details are required
	 * 
	 * @return {PostalPincodeDto} an object of all the details of the PostalPincode
	 */
	@GetMapping("/{pincode}")
    public ResponseEntity<List<PostalPincodeDto>> fetchPostalPincodeDetails(@PathVariable Long pincode) {
		try {
			List<PostalPincodeDto> fetchPostalPincodeDetails = postalPincodeService.fetchPostalPincodeDetails(pincode);        
			logger.info("PostalPincodeController: getPostalPincodeId - foundPostalPincodeAddressData ::: {}",
					fetchPostalPincodeDetails);
			
			return new ResponseEntity(fetchPostalPincodeDetails, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("PostalPincodeController: fetchPostalPincodeDetails - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
    }
}
