package com.scare.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "priceHistory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "priceHistory_id")
	private Integer priceHistory_id;
	
	private String priceHistoryService_id;
	
	@Column(updatable = false)
	private Date updated_on;
	
	@NotNull(message = "Purchase Price cannot be null")
	private Double purchase_price;

	@NotNull(message = "Margin cannot be null")
	private Double margin;

	//Calculate using purchase price and margin
	private Double selling_price;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date effective_from;

	@NotNull(message = "Effective Till cannot be null")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date effective_till;
	
	private String updated_by;
	
}
