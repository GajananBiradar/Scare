package com.scare.model;

import java.util.Date;
import java.util.List;

import com.scare.config.AppConstants;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document(collection = "products")
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private String product_id;

	@NotNull(message = "Product name cannot be null")
	@NotBlank
	private String product_name;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name= "product_type", joinColumns = @JoinColumn(name= "product_id"))
	private List<String> productTypes;
	
	private Integer reward_points;
	
	@Column(updatable = false)
	private Date updated_on;
	
	private String updated_by;
	
	@Column(updatable = false)
	private Date created_on;
	
	private String created_by;

	private String productStatus;
	
	@ManyToOne
	@JoinColumn(name= "categoryId")
	private Category category;
	
	private String category_name;
	
}
