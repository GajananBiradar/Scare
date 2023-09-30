package com.scare.service.impl;

import org.springframework.stereotype.Service;

import com.scare.model.PostalPincode;
import com.scare.service.PostalPincodeService;

@Service
public class PostalPincodeServiceImpl implements PostalPincodeService {

	/*
	 * Fetch postal Address using pincode
	 * 
	 * @param{int} pincode
	 * 
	 * @return{data} postal data
	 */
	@Override
	public PostalPincode getPostalPincode(int pincode) {
//		(`https://api.postalpincode.in/pincode/${pincode}`)
		return null;
	}

	
}
