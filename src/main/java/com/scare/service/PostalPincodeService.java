package com.scare.service;

import java.util.List;

import com.scare.model.PostalPincode;
import com.scare.payloads.PostalPincodeDto;

public interface PostalPincodeService {

	//To get postal address using pin code
	List<PostalPincodeDto> fetchPostalPincodeDetails(Long pincode);
	
}
