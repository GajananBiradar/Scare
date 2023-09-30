package com.scare.model;

import java.util.Date;
import java.util.Set;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document(collection = "brand")
@Entity
@Table(name = "brands")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand {

	@Id
	@Column(name = "brand_id")
	private String brand_id;
	
	@NotNull(message = "Brand name cannot be null")
	@NotBlank
	private String brand_name;
	
	@Column(updatable = false)
	private Date updated_on;
	
	private String updated_by;
	
	@Column(updatable = false)
	private Date created_on;
	
	private String created_by;
	
	@NotNull(message = "Brand status cannot be null")
	@NotBlank
	private String brandStatus;

}
