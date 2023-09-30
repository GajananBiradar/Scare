package com.scare.payloads;

import java.util.Date;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

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
public class BrandDto {

	
	private String brand_id;

	private String brand_name;

	private Date updated_on;

	private String updated_by;

	private Date created_on;

	private String created_by;

	private String brandStatus;

}
