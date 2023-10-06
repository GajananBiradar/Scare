package com.scare.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scare.config.AppConstants;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.model.PostalPincode;
import com.scare.payloads.PostalPincodeDto;
import com.scare.repository.CategoryRepo;
import com.scare.repository.PostalPincodeRepo;
import com.scare.repository.ProductRepo;
import com.scare.service.PostalPincodeService;

@Service
public class PostalPincodeServiceImpl implements PostalPincodeService {

	private static final Logger logger = LoggerFactory.getLogger(PostalPincodeServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate; // finclient

	@Autowired
	PostalPincodeRepo postalPincodeRepo;

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * Fetch postal Address using pincode
	 * 
	 * @param{int} pincode
	 * 
	 * @return{data} postal data
	 */
	@Override
	public List<PostalPincodeDto> fetchPostalPincodeDetails(Long pincode) {
		ResponseEntity<String> response = restTemplate.exchange("https://api.postalpincode.in/pincode/{PINCODE}",
				HttpMethod.GET, null, String.class, pincode);
//		System.out.println("====" + response.getBody());
		logger.info("==== PostalPincodeService: updatePostalPincodeService - PostalPincodeDataNotFoundOnWebsite");
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<PostalPincode> postalPinList = new ArrayList<>();

		try {
			logger.info("--- PostalPincodeService: updatePostalPincodeService - PostalPincodeDataNotFoundOnWebsite");
			
			// Parse the JSON string into a List of Maps
			List<Map<String, Object>> result = objectMapper.readValue(response.getBody(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			logger.info("***** PostalPincodeService: updatePostalPincodeService - PostalPincodeDataNotFoundOnWebsite");
			
			if(result.size()<=0)
			{
				logger.info("PostalPincodeService: updatePostalPincodeService - PostalPincodeDataNotFoundOnWebsite");
				throw new ResourceNotFoundException("fetchPostalPincode", "Pincode", pincode);
			}
			
			if (!result.isEmpty()) {
				// Get the first element of the JSON array
				Map<String, Object> firstElement = result.get(0);


				// Access the "PostOffice" field from the first element
				List<Map<String, Object>> postOffices = (List<Map<String, Object>>) firstElement.get("PostOffice");

				if (!postOffices.isEmpty()) {
					for (Map<String, Object> postD : postOffices) {

						if (this.postalPincodeRepo.findByArea((String) postD.get("Name")) != null) {
							break;
						}

						// Create and return a PostalPincode object with the required data
						PostalPincode postalPincode = new PostalPincode();
						postalPincode.setPincode(pincode);
						postalPincode.setArea((String) postD.get("Name"));
						postalPincode.setCity((String) postD.get("Division"));
						postalPincode.setDistrict((String) postD.get("District"));
						postalPincode.setState((String) postD.get("State"));
						postalPincode.setStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
						PostalPincode savedPostalData = postalPincodeRepo.save(postalPincode);
						postalPinList.add(savedPostalData);
					}
				}
			}
			
		}
		catch (Exception ex) {
			logger.error("PostalPincodeService: getPostalPincodeServices - error ::: {}", ex.getMessage());
			// Handle JSON parsing exceptions
			 ex.printStackTrace();
		}
		List<PostalPincodeDto> postalPincodeDtoList = postalPinList.stream()
				.map((post) -> this.modelMapper.map(post, PostalPincodeDto.class)).collect(Collectors.toList());
		return postalPincodeDtoList;
	}
}
