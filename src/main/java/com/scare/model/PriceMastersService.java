package com.scare.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

	// Service ID
	// private String service_id;

	@OneToOne
	@JoinColumn(name = "serviceDescription", referencedColumnName = "serviceType")
	private ServiceType serviceType;

	@OneToOne
	@JoinColumn(name = "category_name", referencedColumnName = "category_name")
	private Category category;

	@OneToOne
	@JoinColumn(name = "product_name", referencedColumnName = "product_name")
	private Product product;

	@NotNull(message = "Product Type cannot be null")
	@NotBlank
	private String productType;

	@OneToOne
	@JoinColumn(name = "brand_name", referencedColumnName = "brand_name")
	private Brand brand;

	@NotNull(message = "Purchase Price cannot be null")
	private Double purchase_price;

	@NotNull(message = "Margin cannot be null")
	private Double margin;

	//Calculate using purchase price and margin
	private Double selling_price;

	//Find IGST using service Description
//	private Integer igst;
	
	@Column(updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date updated_on;
	
	private String updated_by;
	
	@Column(updatable = false)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date created_on;
	
	private String created_by;
	
	@NotNull(message = "Effective From cannot be null")	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date effective_from;

	@NotNull(message = "Effective Till cannot be null")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date effective_till;

}
