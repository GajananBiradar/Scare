package com.scare.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scare.config.AppConstants;
import com.scare.exceptions.ApiResponse;
import com.scare.exceptions.DuplicateIdException;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.model.Brand;
import com.scare.model.Category;
import com.scare.model.GST;
import com.scare.model.PriceHistory;
import com.scare.model.PriceMastersService;
import com.scare.model.Product;
import com.scare.model.ServiceType;
import com.scare.model.UnitOfMeasurment;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.PriceMastersServiceDto;
import com.scare.payloads.UnitOfMeasurmentDto;
import com.scare.repository.BrandRepo;
import com.scare.repository.CategoryRepo;
import com.scare.repository.GSTRepo;
import com.scare.repository.PriceHistoryRepo;
import com.scare.repository.PriceMastersServiceRepo;
import com.scare.repository.ProductRepo;
import com.scare.repository.ServiceTypeRepo;
import com.scare.service.PriceMasterSService;

import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class PriceMastersSServiceImpl implements PriceMasterSService {

	private static final Logger logger = LoggerFactory.getLogger(PriceMastersSServiceImpl.class);

	@Autowired
	PriceMastersServiceRepo priceMastersServiceRepo;

	@Autowired
	ServiceTypeRepo serviceTypeRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	BrandRepo brandRepo;

	@Autowired
	GSTRepo gstRepo;

	@Autowired
	PriceHistoryRepo priceHistoryRepo;

//	@Autowired
	private ModelMapper modelMapper;

	public PriceMastersSServiceImpl() {
		this.modelMapper = new ModelMapper();
		// Configure the ModelMapper
		this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// Add a custom PropertyMap for product_name mapping
		this.modelMapper.addMappings(new PropertyMap<PriceMastersService, PriceMastersServiceDto>() {
			@Override
			protected void configure() {
				map().setProduct_name(source.getProduct().getProduct_name());
				map().setServiceDescription(source.getServiceType().getServiceType());
				map().setCategory_name(source.getCategory().getCategory_name());
				map().setBrand_name(source.getBrand().getBrand_name());
			}
		});
	}

	/**
	 * Fetch PriceMastersService by Id
	 *
	 * @param {string} priceMastersServiceId
	 * @return {data}
	 */
	@Override
	public PriceMastersServiceDto getPriceMasterServiceById(String priceMastersServiceId) {
		logger.info("PriceMastersService: getPriceMastersServiceById - inputPriceMastersServiceId ::: {}",
				priceMastersServiceId);
		try {
			PriceMastersService priceMastersService = this.priceMastersServiceRepo.findById(priceMastersServiceId)
					.get();
			if (priceMastersService == null) {
				logger.info("PriceMastersService: getPriceMastersServiceById - PriceMastersServiceId NotFound::: {}",
						priceMastersServiceId);
				throw new ResourceNotFoundException("PriceMastersService", "id: ",
						Integer.parseInt(priceMastersServiceId));
			}
			logger.info("PriceMastersService: getPriceMastersServiceById - foundPriceMastersServiceId ::: {}",
					priceMastersServiceId);

			return this.modelMapper.map(priceMastersService, PriceMastersServiceDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error(
					"PriceMastersService: getPriceMastersServiceById - inputPriceMastersService Id - error ::: PriceMastersService not found: {}",
					ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("PriceMastersService: getPriceMastersServiceById - inputPriceMastersServiceId - error ::: {}",
					ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Fetch all PriceMastersService by pagination
	 *
	 * @param {Integer, Integer, String, String} pageNumber, pageSize, sortBy,
	 *                  sortDir
	 * @return {data}
	 */
	@Override
	public List<PriceMastersServiceDto> getPriceMasterServicesByPaginationAndFilter(Integer pageNumber,
			Integer pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Fetch all PriceMastersService
	 *
	 * @return {data}
	 */
	@Override
	public List<PriceMastersServiceDto> getAllPriceMasterServices() {
		logger.info("PriceMastersService: getAllPriceMastersServices ");

		try {
			List<PriceMastersService> allPriceMastersServices = this.priceMastersServiceRepo.findAll();
			List<PriceMastersServiceDto> allPriceMastersService = allPriceMastersServices.stream().map(
					(priceMastersService) -> this.modelMapper.map(priceMastersService, PriceMastersServiceDto.class))
					.collect(Collectors.toList());
			int size = allPriceMastersService.size();

			// Log the size and one PriceMastersService
			logger.info("PriceMastersService: getAllPriceMastersServices - size ::: {}", size);
			if (size > 0) {
				logger.info("PriceMastersService: getAllPriceMastersServices - one PriceMastersService ::: {}",
						allPriceMastersService.get(0));
			}

			return allPriceMastersService;
		} catch (Exception ex) {
			logger.error("PriceMastersService: getAllPriceMastersServices - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<PriceMastersServiceDto> getPriceMasterServiceByOptions(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save PriceMastersService
	 *
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public PriceMastersServiceDto createPriceMasterService(PriceMastersServiceDto priceMastersServiceDto) {
		logger.info("PriceMastersService: createPriceMastersService - inputPriceMastersServiceDto ::: {}",
				priceMastersServiceDto);

		PriceMastersService priceMastersService = this.modelMapper.map(priceMastersServiceDto,
				PriceMastersService.class);
		try {
			ServiceType findByService_Type = this.serviceTypeRepo
					.findByService_Type(priceMastersServiceDto.getServiceDescription());
			Category findByCategory_name = this.categoryRepo
					.findByCategory_name(priceMastersServiceDto.getCategory_name());
			Product findByProduct_name = this.productRepo.findByProduct_name(priceMastersServiceDto.getProduct_name());
			Brand findByBrand_name = this.brandRepo.findByBrand_name(priceMastersServiceDto.getBrand_name());

			// Configure the mapping for product_name
//	        modelMapper.addMappings(new PropertyMap<PriceMastersService, PriceMastersServiceDto>() {
//	            @Override
//	            protected void configure() {
//	                map().setProduct_name(source.getProduct().getProduct_name());
//	                map().setServiceDescription(source.getServiceType().getServiceType());	                
//	            }
//	        });

			priceMastersService.setPriceMasterService_id(UUID.randomUUID().toString().split("-")[0]);
			priceMastersService.setServiceType(findByService_Type);
			priceMastersService.setCategory(findByCategory_name);

			priceMastersService.setProduct(findByProduct_name);
			priceMastersService.setProductType(priceMastersServiceDto.getProductType());

			priceMastersService.setBrand(findByBrand_name);

			// Adding purchase price, margin and calculating selling price
			Double purchase_price = priceMastersServiceDto.getPurchase_price();
			Double margin = priceMastersServiceDto.getMargin();

			priceMastersService.setPurchase_price(purchase_price);
			priceMastersService.setMargin(margin);

			Double sellingPrice = (purchase_price * (margin / 100)) + purchase_price;
			priceMastersService.setSelling_price(sellingPrice);

			priceMastersService.setEffective_from(priceMastersServiceDto.getEffective_from());
			priceMastersService.setEffective_till(priceMastersServiceDto.getEffective_till());
			priceMastersService.setCreated_on(new Date());
			priceMastersService.setUpdated_on(new Date());
			priceMastersService.setCreated_by("Jay");
			priceMastersService.setUpdated_by("Jay");

			PriceMastersService savedPriceMastersService = this.priceMastersServiceRepo.save(priceMastersService);
			logger.info("PriceMastersService: getAllPriceMastersServices - savedPriceMastersService ::: {}",
					savedPriceMastersService);

			logger.info(
					"PriceMastersService: createPriceMastersService - inputPriceMastersServiceDto - Storing price history data");

			// Setting Price History data
			PriceHistory priceHistory = new PriceHistory();
			priceHistory.setPriceHistoryService_id(savedPriceMastersService.getPriceMasterService_id());
			priceHistory.setUpdated_on(new Date());
			priceHistory.setPurchase_price(priceMastersServiceDto.getPurchase_price());
			priceHistory.setMargin(priceMastersServiceDto.getMargin());
			priceHistory.setSelling_price(sellingPrice);
			priceHistory.setEffective_from(priceMastersServiceDto.getEffective_from());
			priceHistory.setEffective_till(priceMastersService.getEffective_till());
			priceHistory.setUpdated_by(savedPriceMastersService.getUpdated_by());
			PriceHistory savePriceHistory = priceHistoryRepo.save(priceHistory);
			logger.info("PriceMastersService: createPriceMastersService - PriceHistorySaved ::: {}", savePriceHistory);

			return this.modelMapper.map(savedPriceMastersService, PriceMastersServiceDto.class);

		} catch (Exception ex) {
			logger.error("PriceMastersService: createPriceMastersService - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Update PriceMastersService
	 *
	 * @param {PriceMastersServiceDto, string}
	 * @return {data}
	 */
	@Override
	public PriceMastersServiceDto updatePriceMasterService(PriceMastersServiceDto priceMastersServiceDto) {
		logger.info("PriceMastersService: updatePriceMastersService - inputPriceMastersServiceDto  ::: {}",
				priceMastersServiceDto);
		try {
			String priceMastersServiceId = priceMastersServiceDto.getPriceMasterService_id();
			PriceMastersService priceMastersService = this.priceMastersServiceRepo.findById(priceMastersServiceId)
					.get();
			if (priceMastersService != null) {
				logger.info("PriceMastersService: updatePriceMastersService - foundPriceMastersServiceId ::: {}",
						priceMastersService);

				priceMastersService.setCreated_on(priceMastersServiceDto.getCreated_on());
				priceMastersService.setUpdated_on(new Date());

				// Adding purchase price, margin and selling price
				Double purchase_price = priceMastersServiceDto.getPurchase_price();
				logger.info("PriceMastersService: updatePriceMastersService - Fetch Purchase price ::: {}",
						purchase_price);
				Double margin = priceMastersServiceDto.getMargin();
				logger.info("PriceMastersService: updatePriceMastersService - Fetch Margin ::: {}", margin);

				priceMastersService.setPurchase_price(purchase_price);
				priceMastersService.setMargin(margin);
				Double sellingPrice = (purchase_price * (margin / 100)) + purchase_price;
				logger.info("PriceMastersService: updatePriceMastersService - Fetch Selling price ::: {}",
						sellingPrice);
				priceMastersService.setSelling_price(sellingPrice);

				PriceMastersService updatePriceMastersService = this.priceMastersServiceRepo.save(priceMastersService);
				logger.info("PriceMastersService: updatePriceMastersServices - savedPriceMastersService ::: {}",
						updatePriceMastersService);

				logger.info("PriceMastersService: updatePriceMastersService - PriceHistory Data Storing ");
				// Setting Price History data
				PriceHistory priceHistory = new PriceHistory();
				priceHistory.setPriceHistoryService_id(priceMastersServiceId);
				priceHistory.setUpdated_on(new Date());
				priceHistory.setPurchase_price(priceMastersServiceDto.getPurchase_price());
				priceHistory.setMargin(priceMastersServiceDto.getMargin());
				priceHistory.setSelling_price(sellingPrice);
				priceHistory.setEffective_from(priceMastersServiceDto.getEffective_from());
				priceHistory.setEffective_till(priceMastersService.getEffective_till());
				priceHistory.setUpdated_by(updatePriceMastersService.getUpdated_by());
				PriceHistory updateHistory = priceHistoryRepo.save(priceHistory);
				logger.info("PriceMastersService: updatePriceMastersService - PriceHistoryUpdated ::: {}", updateHistory);

				return this.modelMapper.map(updatePriceMastersService, PriceMastersServiceDto.class);
			} else {
				logger.info("PriceMastersService: updatePriceMastersService - PriceMastersServiceIdNotFound ::: {}",
						priceMastersServiceId);
				throw new ResourceNotFoundException("PriceMastersService", "id: ",
						Integer.parseInt(priceMastersServiceId));
			}
		} catch (Exception ex) {
			logger.error("PriceMastersService: updatePriceMastersService - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Delete PriceMastersService
	 *
	 * @param {string}
	 * @return {message}
	 */
	@Override
	public void deletePriceMasterService(String priceMastersServiceId) {
		logger.info("PriceMastersService: deletePriceMastersService - inputPriceMastersServiceId ::: {}",
				priceMastersServiceId);
		try {
			PriceMastersService priceMastersService = this.priceMastersServiceRepo.findById(priceMastersServiceId)
					.get();
			if (priceMastersService != null) {
				logger.info("PriceMastersService: deletePriceMastersService - foundPriceMastersServiceId ::: {}",
						priceMastersService);
				this.priceMastersServiceRepo.delete(priceMastersService);
			} else {
				logger.info("PriceMastersService: deletePriceMastersService - PriceMastersServiceIdNotFound ::: {}",
						priceMastersServiceId);
				throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(priceMastersServiceId));
			}
		} catch (Exception ex) {
			logger.error("PriceMastersService: deletePriceMastersService - error ::: {}", ex.getMessage());
			throw ex;
		}
	}
}
