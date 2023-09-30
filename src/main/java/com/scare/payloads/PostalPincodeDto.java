package com.scare.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PostalPincodeDto {

	private String area;
	
	@NotNull(message = "pincode can not be null")
	@NotBlank
	private Integer pincode;
	
	private String city;
	
	private String district;
	
	private String state;
	
	private String status;
	
}
