package com.scare.model;

import jakarta.persistence.Column;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document(collection = "postalPincode")
@Entity
@Table(name = "postalPincode")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostalPincode {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private String product_id;
	
	private String area;
	
	private Integer pincode;
	
	private String city;
	
	private String district;
	
	private String state;
	
	private String status;
	
}
