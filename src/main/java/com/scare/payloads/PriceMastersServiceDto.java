package com.scare.payloads;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scare.model.ServiceType;

import jakarta.persistence.Column;
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

	//private String service_id;  //Fetch from service Description
	
	private ServiceType serviceType;

	private String serviceDescription;

	private String category_name;

	private String product_name;

	private String productType;

	private String brand_name;

	private Double purchase_price;

	private Double margin;

	private Double selling_price; //Calculate using purchase price and margin

	//private Integer igst;  //Fetch from service Description using HSN code and GST table
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date updated_on;
	
	private String updated_by;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date created_on;
	
	private String created_by;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date effective_from;

	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date effective_till;

	
}
