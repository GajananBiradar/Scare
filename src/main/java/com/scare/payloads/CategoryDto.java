package com.scare.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private String category_id;
	
	@Size(max = 20, min = 2, message = "Category name should be between 2 to 20 charcters only")
	private String category_name;
	
	private Date updated_on;
	
	private String updated_by;

	private Date created_on;
	
	private String created_by;
	
	private String categoryStatus;
		
	
	
}
