package com.scare.payloads;

import java.util.Date;
import java.util.List;

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
public class ProductDto {
	
	private String product_id;

	@Size(max = 20, min = 2, message = "Product name should be between 2 to 20 charcters only")
	private String product_name;
	
	private List<String> productTypes;

//	@Pattern(regexp = "([1-9]|[1-9][0-9]|100)", message = "Reward points must be between 1 to 100 points.")
	private Integer reward_points;

	private Date updated_on;

	private String updated_by;

	private Date created_on;

	private String created_by;
	
	private String productStatus;

//	private CategoryDto categoryDto;
	
	private String category_name;
		
}
