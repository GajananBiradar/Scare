package com.scare.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.scare.config.AppConstants;
import com.scare.exceptions.ApiResponse;
import com.scare.exceptions.DataAccessErrorException;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.payloads.CategoryDto;
import com.scare.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/category")
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/h/hello")
	public String hello() {
		System.out.println("hello hello");
		return "Hello, world!";
	}

	/*
	 * Returns a category based on entered/selected category Id
	 * 
	 * @param {String} id the id of the category for which details are required
	 * 
	 * @return {CategoryDto} an object of all the details of the category
	 */
	@GetMapping("/{catId}")
	public ResponseEntity<?> getCategoryById(@PathVariable("catId") String catId) {
		logger.info("CategoryController: getCategoryId - inputCategoryId ::: {}", catId);
		/*
		 * try { CategoryDto categoryById = this.categoryService.getCategoryById(catId);
		 * logger.info("CategoryController: getCategoryId - foundCategoryId ::: {}",
		 * catId);
		 * 
		 * return ResponseEntity.ok(categoryById); } catch (ResourceNotFoundException
		 * ex) { return ResponseEntity.status(HttpStatus.NOT_FOUND) .body(new
		 * ApiResponse(ex.getMessage(), false)); } catch (Exception ex) {
		 * logger.error("CategoryController: getCategoryId - error ::: {}",
		 * ex.getMessage()); return
		 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(new
		 * ApiResponse("Internal Server Error", false)); }
		 */

		try {
			CategoryDto categoryById = this.categoryService.getCategoryById(catId);
			logger.info("CategoryController: getCategoryId - foundCategoryId ::: {}", catId);

			return new ResponseEntity(categoryById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			throw new ResourceNotFoundException("Resource", "category", Integer.parseInt(catId));
		} catch (NoSuchElementException ex) {
			logger.error("CategoryController: getCategoryId - NoSuchElementException error ::: {}", ex.getMessage());
			ApiResponse errorResponse = new ApiResponse("No such Id is found", false);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception ex) {
			// Log the error
			logger.error("CategoryController: getCategoryId - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Returns list of categories
	 * 
	 * @return {Array<CategoryDto>}
	 */
	@GetMapping("/")
	public ResponseEntity<?> getAllCategories() {
		logger.info("CategoryController: getAllCategories ");
		try {
			List<CategoryDto> categories = this.categoryService.getAllCategories();
			if (categories.isEmpty()) {
				logger.error("CategoryController: getAllCategories - categories.size() ::: {}", categories.size());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(categories, HttpStatus.OK);
//		} catch (DataAccessException ex) {
//			logger.error("CategoryController: getAllCategories - NoSuchElementException error ::: {}", ex.getMessage());
//			ApiResponse errorResponse = new ApiResponse("DataAccessException is found", false);
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
		catch (DataAccessException ex) {
	        logger.error("CategoryController: getAllCategories - DataAccessException error ::: {}", ex.getMessage());
	        throw new DataAccessErrorException("DataAccessException is found"); // Example usage of DataAccessErrorException
		}catch (Exception ex) {
			// Log the error
			logger.error("CategoryController: getAllCategories - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Creates and enters into db a new category
	 * 
	 * @param{pageNumber, pageSize, sortBy, sortDir} object containing all details
	 * of the category to be entered
	 * 
	 * @return{List<CategoryDto>} all entries of category after successfully
	 * inserting into db
	 */
	@GetMapping("/getByPaginationAndFilter")
	private ResponseEntity<List<CategoryDto>> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		List<CategoryDto> allProducts = this.categoryService.getCategoryByPaginationAndFilter(pageNumber, pageSize,
				sortBy, sortDir);

		if (allProducts.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

	/*
	 * Creates and enters into db a new category
	 * 
	 * @param{categoryDto} object containing all details of the category to be
	 * entered
	 * 
	 * @return{CategoryDto} all entries of category after successfully inserting
	 * into db
	 */
	@PostMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<CategoryDto> createCateogry(@RequestBody CategoryDto categoryDto) {
		if (categoryDto == null) {
			logger.info("CategoryController: createCateogry - inputCategoryDto - categoryDto is null ::: {}",
					categoryDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		logger.info("CategoryController: createCateogry - inputCategoryDto ::: {}", categoryDto);
		try {
			CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
			return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
		} catch (Exception ex) {
			// Log the error
			logger.error("CategoryController: createCategory - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Update a category by ID
	 * 
	 * @param{CategoryDto, catId} object containing all the details about category
	 * and id of category
	 * 
	 * @return{CategoryDto} returns updated category
	 */
	@PutMapping("/")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
		logger.info("CategoryController: updateCategory - categoryDto ::: {}", categoryDto);
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto);

		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
	}

	/*
	 * Delete a category by categoryId
	 * 
	 * @param{catId} takes category Id
	 * 
	 * @return{String} confirmation of successful deletion
	 */
	@DeleteMapping("/{catId}")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('NORMAL')")
	public ResponseEntity<ApiResponse> deleteCateegory(@PathVariable("catId") String catId) {
		logger.info("CategoryController: deleteCateegory - catId ::: {}", catId);

		try {
			this.categoryService.deleteCategory(catId);
			return ResponseEntity.ok(new ApiResponse("Category deleted successfully", true));
		} catch (ResourceNotFoundException ex) {
			logger.error("CategoryController: deleteCategory - inputCategoryId - error ::: Category not found: {}",
					ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("CategoryController: deleteCategory - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
