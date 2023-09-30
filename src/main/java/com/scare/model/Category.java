package com.scare.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document(collection = "category")
@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
	private String category_id;
	
	@Column(unique = true)
	@NotNull(message = "Category name cannot be null")
	@NotBlank
	private String category_name;
	
	@Column(updatable = false)
	private Date updated_on;
	
	private String updated_by;
	
	@Column(updatable = false)
	private Date created_on;
	
	private String created_by;
	
	@NotNull(message = "Category Status cannot be null")
	@NotBlank
	private String categoryStatus;
	
	@OneToMany(mappedBy = "category")
	private List<Product> product= new ArrayList<>();
		
}
