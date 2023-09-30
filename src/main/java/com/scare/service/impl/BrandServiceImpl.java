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
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.model.Brand;
import com.scare.model.Category;
import com.scare.model.Product;
import com.scare.payloads.BrandDto;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.ProductDto;
import com.scare.repository.BrandRepo;
import com.scare.repository.CategoryRepo;
import com.scare.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

	@Autowired
	BrandRepo brandRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Fetch brand by Id
	 * 
	 * @param {string} brandId
	 * @return {data}
	 */
	@Override
	public BrandDto getBrandById(String brandId) {
		logger.info("BrandService: getBrandId - inputBrandId ::: {}", brandId);

		try {
			Brand brand = this.brandRepo.findById(brandId).get();
			if (brand == null) {
				logger.info("BrandService: getBrandId - BrandId NotFound::: {}", brandId);
				throw new ResourceNotFoundException("Brand", "id: ", Integer.parseInt(brandId));
			}
			logger.info("BrandService: getBrandId - foundBrandId ::: {}", brand);

			return this.modelMapper.map(brand, BrandDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error("BrandService: getBrandId - inputBrand Id - error ::: Brand not found: {}", ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("BrandService: getBrandId - inputBrandId - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<BrandDto> getBrandByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Fetch all brands
	 *
	 * @return {data}
	 */
	@Override
	public List<BrandDto> getAllBrands() {
		logger.info("BrandService: getAllBrands ");

		try {
			List<Brand> allBrands = this.brandRepo.findAll();
			List<BrandDto> allBrand = allBrands.stream().map((brand) -> this.modelMapper.map(brand, BrandDto.class))
					.collect(Collectors.toList());
			int size = allBrand.size();

			// Log the size and one Brand
			logger.info("BrandService: getAllBrands - size ::: {}", size);
			if (size > 0) {
				logger.info("BrandService: getAllBrands - one Brand ::: {}", allBrand.get(0));
			}

			return allBrand;
		} catch (Exception ex) {
			logger.error("BrandService: getAllBrands - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<BrandDto> getBrandByOptions(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save Brand
	 * 
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public BrandDto createBrand(BrandDto brandDto) {
		logger.info("BrandService: createBrand - inputBrandDto ::: {}", brandDto);

		Brand brand = this.modelMapper.map(brandDto, Brand.class);
		try {
			brand.setBrand_id(UUID.randomUUID().toString().split("-")[0]);
			brand.setCreated_by("Gajanan");
			brand.setCreated_on(new Date());
			brand.setUpdated_by("Gajanan");
			brand.setUpdated_on(new Date());
			if (brandDto.getBrandStatus().equalsIgnoreCase("publish")) {
				brand.setBrandStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
			} else {
				brand.setBrandStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
			}

			Brand savedBrand = this.brandRepo.save(brand);
			logger.info("BrandService: getAllBrands - savedBrand ::: {}", savedBrand);

			return this.modelMapper.map(savedBrand, BrandDto.class);
		} catch (Exception ex) {
			logger.error("BrandService: createBrand - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Update brand
	 * 
	 * @param {BrandDto, string}
	 * @return {data}
	 */
	@Override
	public BrandDto updateBrand(BrandDto brandDto) {
		logger.info("BrandService: updateBrand - inputBrandDto ::: {}", brandDto);
		try {
			String brandId = brandDto.getBrand_id();
			Brand brand = this.brandRepo.findById(brandId).get();
			if (brand != null) {
				logger.info("CategoryService: updateCategory - foundCategoryId ::: {}", brand);

				brand.setBrand_name(brandDto.getBrand_name());
				brand.setUpdated_by("Gajanan");
				brand.setUpdated_on(new Date());
				if (brandDto.getBrandStatus().equalsIgnoreCase("publish")) {
					brand.setBrandStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
				} else {
					brand.setBrandStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
				}
				Brand updateBrand = this.brandRepo.save(brand);
				logger.info("BrandService: getAllBrands - savedBrand ::: {}", updateBrand);

				return this.modelMapper.map(updateBrand, BrandDto.class);
			} else {
				logger.info("BrandService: updateBrand - BrandIdNotFound ::: {}", brandId);
				throw new ResourceNotFoundException("Brand", "id: ", Integer.parseInt(brandId));

			}
		} catch (Exception ex) {
			logger.error("BrandService: updateBrand - error ::: {}", ex.getMessage());
			throw ex;
		}

	}

	/**
	 * Delete brand
	 * 
	 * @param {string}
	 * @return {message}
	 */
	@Override
	public void deleteBrand(String brandId) {
		logger.info("BrandService: deleteBrand - inputBrandId ::: {}", brandId);
		try {
			Brand brand = this.brandRepo.findById(brandId).get();
			if (brand != null) {
				logger.info("BrandService: deleteBrand - foundBrandId ::: {}", brand);
				this.brandRepo.delete(brand);
			} else {
				logger.info("BrandService: updateBrand - BrandIdNotFound ::: {}", brandId);
				throw new ResourceNotFoundException("Brand", "id: ", Integer.parseInt(brandId));
			}
		} catch (Exception ex) {
			logger.error("BrandService: deleteBrand - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

}
