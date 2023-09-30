package com.scare.service;

import com.scare.model.PostalPincode;

public interface PostalPincodeService {

	//To get postal address using pincode
	PostalPincode getPostalPincode(int pincode);
	
}
