package com.scare.payloads;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UnitOfMeasurmentDto {

	private String unitOfMeasurment_id;

	@NotNull(message = "Unit Of Measurment name cannot be null")
	@NotBlank
	@Size(max = 20, min = 2, message = "Unit Of Measurment name should be between 2 to 20 charcters only")
	String unitOfMeasurment_name;
	
	private String unitOfMeasurment_Desc;

	private Date updated_on;
	
	private String updated_by;
	
	private Date created_on;
	
	private String created_by;

	private String unitOfMeasurmentStatus;
	
}
