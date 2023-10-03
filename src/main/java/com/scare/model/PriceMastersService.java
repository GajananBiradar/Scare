package com.scare.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "priceMasterService")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceMastersService {

	@Id
	@Column(name = "priceMasterService_id")
	private String priceMasterService_id;

	@NotNull(message = "Product name cannot be null")
	@NotBlank
	private Double purchase_price;	
	
	private Double margin;
	
	private Double selling_price;
	
	private Date effective_from;
	
	private Date effective_till;
	
}
