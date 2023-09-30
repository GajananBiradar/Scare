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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.scare.exceptions.ApiResponse;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.ProductDto;
import com.scare.service.CategoryService;
import com.scare.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping("/h/hello")
	public String hello() {
		System.out.println("hello hello");
		return "Hello, world!";
	}

	/*
	 * Returns a Product based on entered/selected Product Id
	 * 
	 * @param {String} id the id of the Product for which details are required
	 * 
	 * @return {ProductDto} an object of all the details of the Product
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") String productId) {
		logger.info("ProductController: getProductId - inputProductId ::: {}", productId);

		if (this.productService.getProductById(productId) == null) {
			throw new ResourceNotFoundException("Product", "id: ", Integer.parseInt(productId));
		}
		try {
			ProductDto productById = this.productService.getProductById(productId);
			logger.info("ProductController: getProductId - foundProductId ::: {}", productId);

			return new ResponseEntity(productById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("ProductController: getProductId - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Returns list of Products
	 * 
	 * @return {Array<ProductDto>}
	 */
	@GetMapping("/")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		logger.info("ProductController: getAllProducts ");
		try {
			List<ProductDto> products = this.productService.getAllProducts();
			if (products.isEmpty()) {
				logger.error("ProductController: getAllProducts - products.size() ::: {}", products.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception ex) {
			// Log the error
			logger.error("ProductController: getAllProducts - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Creates and enters into DB a new Product
	 * 
	 * @param{pageNumber, pageSize, sortBy, sortDir} object containing all details
	 * of the category to be entered
	 * 
	 * @return{List<ProductDto>} all entries of category after successfully
	 * inserting into DB
	 */
//	@GetMapping("/getByPaginationAndFilter")
//	private ResponseEntity<List<ProductDto>> getAllProducts()


	/*
	 * Creates and enters into DB a new Product
	 * 
	 * @param{productDto} object containing all details of the Product to be entered
	 * 
	 * @return{ProductDto} all entries of Product after successfully inserting into
	 * DB
	 */
	@PostMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
		if (productDto == null) {
			logger.info("ProductController: createProduct - inputProductDto - ProductDto is null ::: {}", productDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("ProductController: createProduct - inputProductDto ::: {}", productDto);
		try {
			ProductDto createProduct = this.productService.createProduct(productDto);
			return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
		} catch (Exception ex) {
			// Log the error
			logger.error("ProductController: createProduct - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Update a Product by ID
	 * 
	 * @param{ProductDto, productId} object containing all the details about Product and
	 * id of Product
	 * 
	 * @return{ProductDto} returns updated Product
	 */
	@PutMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto) {
		logger.info("ProductController: updateProduct - ProductDto ::: {}", productDto);

		ProductDto updatedProduct = this.productService.updateProduct(productDto);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	/*
	 * Delete a product by productId
	 * 
	 * @param{productId} takes product Id
	 * 
	 * @return{String} confirmation of successful deletion
	 */
	@DeleteMapping("/{productId}")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") String productId) {
		logger.info("ProductController: deleteProduct - productId ::: {}", productId);

		try {
			this.productService.deleteProduct(productId);
			return ResponseEntity.ok(new ApiResponse("Product deleted successfully", true));
		} catch (ResourceNotFoundException ex) {
			logger.error("ProductController: deleteProduct - inputProductId - error ::: Product not found: {}",
					ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("ProductController: deleteProduct - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
