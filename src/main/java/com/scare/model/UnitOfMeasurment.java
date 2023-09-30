package com.scare.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unitOfMeasurment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnitOfMeasurment {

	@Id
	@Column(name = "unitOfMeasurment_id")
	private String unitOfMeasurment_id;

	private String unitOfMeasurment_name;
	
	private String unitOfMeasurment_Desc;

	@Column(updatable = false)
	private Date updated_on;
	
	private String updated_by;
	
	@Column(updatable = false)
	private Date created_on;
	
	private String created_by;

	private String unitOfMeasurmentStatus;
	
}
