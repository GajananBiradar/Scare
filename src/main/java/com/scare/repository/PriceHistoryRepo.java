package com.scare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scare.model.PriceHistory;

@Repository
public interface PriceHistoryRepo extends JpaRepository<PriceHistory, Integer>{

	@Query("SELECT h FROM PriceHistory h where h.priceHistoryService_id= ?1")
	List<PriceHistory> findByPriceHistoryServiceId(String priceHistoryService_id);
	
}

