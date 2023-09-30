package com.scare.payloads;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class GSTDto {
	
	private String gst_id;
	
	private Long hsn_code;
	
	@Size(max = 20, min = 2, message = "GST Descsription should be between 2 to 20 charcters only")
	private String gstDesc;
	
	private Integer igst;
	
	private Integer sgst;
	
	private Integer cgst;
	
}