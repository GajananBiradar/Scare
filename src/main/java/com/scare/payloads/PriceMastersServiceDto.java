package com.scare.payloads;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceMastersServiceDto {

	private String priceMasterService_id;

	private String service_id;

	private String serviceType;

	private String category_name;

	private String product_name;

	private List<String> productTypes;

	private String brand_name;

	private Double purchase_price;

	private Double margin;

	private Double selling_price;

	private Integer igst;

	private Date effective_from;

	private Date effective_till;
	
}
