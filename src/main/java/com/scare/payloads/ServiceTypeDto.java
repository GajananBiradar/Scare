package com.scare.payloads;

import java.util.Date;
import java.util.List;

import com.scare.model.GST;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ServiceTypeDto {

	private String service_id;

	@Size(max = 20, min = 2, message = "Service Type should be between 2 to 20 charcters only")
	private String serviceType;

	private String abbrivation;

	private GST gst;
	
	@NotNull(message = "hsn_code cannot be null")
	private Long hsn_code;
}
