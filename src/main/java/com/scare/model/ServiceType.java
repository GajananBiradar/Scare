package com.scare.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "serviceType")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceType {

	@Id
	@Column(name = "service_id")
	private String service_id;
	
	@Column(unique = true)
	@NotNull(message = "Service Type cannot be null")
	@NotBlank
	private String serviceType;
	
	@NotNull(message = "Abbrivation cannot be null")
	@NotBlank
	private String abbrivation;
	
	@OneToOne
	@JoinColumn(name = "hsn_code", referencedColumnName = "hsn_code")
	private GST gst;
	
	@Transient
	private Long hsn_code;
	
	//Remaining Business Type	
}
