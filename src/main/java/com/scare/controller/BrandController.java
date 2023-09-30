package com.scare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scare.exceptions.ApiResponse;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.payloads.BrandDto;
import com.scare.payloads.ProductDto;
import com.scare.service.BrandService;
import com.scare.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/brand")
public class BrandController {

	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;

	@GetMapping("/h/hello")
	public String hello() {
		System.out.println("hello hello");
		return "Hello, world!";
	}

	/*
	 * Returns a Brand based on entered/selected Brand Id
	 * 
	 * @param {String} id the id of the Brand for which details are required
	 * 
	 * @return {BrandDto} an object of all the details of the Brand
	 */
	@GetMapping("/{brandId}")
	public ResponseEntity<BrandDto> getBrandById(@PathVariable("brandId") String brandId) {
		logger.info("BrandController: getBrandId - inputBrandId ::: {}", brandId);

		if (this.brandService.getBrandById(brandId) == null) {
			throw new ResourceNotFoundException("Brand", "id: ", Integer.parseInt(brandId));
		}
		try {
			BrandDto brandById = this.brandService.getBrandById(brandId);
			logger.info("BrandController: getBrandId - foundBrandId ::: {}", brandId);

			return new ResponseEntity(brandById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("ProductController: getProductId - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Returns list of Brands
	 * 
	 * @return {Array<BrandDto>}
	 */
	@GetMapping("/")
	public ResponseEntity<List<BrandDto>> getAllBrands() {
		logger.info("BrandController: getAllBrands ");
		try {
			List<BrandDto> brands = this.brandService.getAllBrands();
			if (brands.isEmpty()) {
				logger.error("BrandController: getAllBrands - products.size() ::: {}", brands.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(brands, HttpStatus.OK);
		} catch (Exception ex) {
			// Log the error
			logger.error("BrandController: getAllProducts - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Creates and enters into DB a new Brand
	 * 
	 * @param{pageNumber, pageSize, sortBy, sortDir} object containing all details
	 * of the Brand to be entered
	 * 
	 * @return{List<BrandDto>} all entries of Brand after successfully
	 * inserting into DB
	 */
//	@GetMapping("/getByPaginationAndFilter")
//	private ResponseEntity<List<ProductDto>> getAllProducts()

	/*
	 * Creates and enters into DB a new Brand
	 * 
	 * @param{BrandDto} object containing all details of the Brand to be entered
	 * 
	 * @return{BrandDto} all entries of Brand after successfully inserting into
	 * DB
	 */
	@PostMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<BrandDto> createBrand(@RequestBody BrandDto brandDto) {
		if (brandDto == null) {
			logger.info("BrandController: createBrand - inputBrandDto - BrandDto is null ::: {}", brandDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("BrandController: createBrand - inputBrandDto ::: {}", brandDto);
		try {
			BrandDto createBrand = this.brandService.createBrand(brandDto);
			return new ResponseEntity<>(createBrand, HttpStatus.CREATED);
		} catch (Exception ex) {
			// Log the error
			logger.error("BrandController: createBrand - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Update a Brand by ID
	 * 
	 * @param{BrandDto, catId} object containing all the details about Brand and
	 * id of Brand
	 * 
	 * @return{BrandDto} returns updated Brand
	 */
	@PutMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<BrandDto> updateBrand(@Valid @RequestBody BrandDto brandDto) {
		logger.info("BrandController: updateBrand - BrandDto, catId ::: {}", brandDto);

		BrandDto updatedBrand = this.brandService.updateBrand(brandDto);

		return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
	}

	/*
	 * Delete a Brand by brandId
	 * 
	 * @param{brandId} takes brand Id
	 * 
	 * @return{String} confirmation of successful deletion
	 */
	@DeleteMapping("/{brandId}")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<ApiResponse> deleteBrand(@PathVariable("brandId") String brandId) {
		logger.info("BrandController: deleteBrand - brandId ::: {}", brandId);

		try {
			this.brandService.deleteBrand(brandId);
			return ResponseEntity.ok(new ApiResponse("Brand deleted successfully", true));
		} catch (ResourceNotFoundException ex) {
			logger.error("BrandController: deleteBrand - inputBrandId - error ::: Brand not found: {}",
					ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("BrandController: deleteBrand - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
