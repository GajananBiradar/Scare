package com.scare.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gst")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GST {

	@Id
	@Column(name = "gst_id")
	private String gst_id;
	
	@Column(unique = true)
	@NotNull(message = "HSN_Code cannot be null")
	private Long hsn_code;
	
	@NotNull(message = "GST Descsription cannot be null")
	@NotBlank
	private String gstDesc;
	
	private Integer igst;
	
	@Column(columnDefinition = "INTEGER default 9", insertable = false)
	private Integer sgst;
	
	@Column(columnDefinition = "INTEGER default 9", insertable = false)
	private Integer cgst;
	
}