package com.scare.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scare.config.AppConstants;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.model.Category;
import com.scare.model.Product;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.ProductDto;
import com.scare.repository.CategoryRepo;
import com.scare.repository.ProductRepo;
import com.scare.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Fetch category by Id
	 * 
	 * @param {string} categoryId
	 * @return {data}
	 */
	@Override
	public CategoryDto getCategoryById(String categoryId) {
		logger.info("CategoryService: getCategoryId - inputCategoryId ::: {}", categoryId);
		/*
		 * Optional<Category> optionalCategory = this.categoryRepo.findById(categoryId);
		 * if (optionalCategory.isEmpty()) {
		 * logger.info("CategoryService: getCategoryId - CategoryId NotFound::: {}",
		 * categoryId); throw new ResourceNotFoundException("Category", "id: ",
		 * Integer.parseInt(categoryId)); } Category category = optionalCategory.get();
		 * logger.info("CategoryService: getCategoryId - foundCategoryId ::: {}",
		 * category); return this.modelMapper.map(category, CategoryDto.class);
		 */
		try {
			Category category = this.categoryRepo.findById(categoryId).get();
			if (category == null) {
				logger.info("CategoryService: getCategoryId - CategoryId NotFound::: {}", categoryId);
				throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(categoryId));
			}
			logger.info("CategoryService: getCategoryId - foundCategoryId ::: {}", category);
			return this.modelMapper.map(category, CategoryDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error(
					"CategoryService: getCategoryId - inputCategory Id - ResourceNotFoundException error ::: Category not found: {}",
					ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (NoSuchElementException ex) {
			logger.error(
					"CategoryService: getCategoryId - inputCategory Id - NoSuchElementException error ::: Category not found: {}",
					ex.getMessage());
			// Propagate the NoSuchElementException to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("CategoryService: getCategoryId - inputCategorId - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Fetch all category by pagination
	 * 
	 * @param {Integer, Integer, String, String} pageNumber, pageSize, sortBy,
	 *                  sortDir
	 * @return {data}
	 */
	@Override
	public List<CategoryDto> getCategoryByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Category> allCategories = this.categoryRepo.findAll(p);

		List<Category> allCategory = allCategories.getContent();
		List<CategoryDto> categories = allCategory.stream()
				.map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

		return categories;
	}

	/**
	 * Fetch all categories
	 *
	 * @return {data}
	 */
	@Override
	public List<CategoryDto> getAllCategories() {
		logger.info("CategoryService: getAllCategories ");
		try {
			List<Category> allCategories = this.categoryRepo.findAll();
			if (allCategories.isEmpty()) {
				logger.info("CategoryService: getAllCategories - No categories found");
				// Return an empty list or handle the empty result as needed
				return new ArrayList<>();
			}

			List<CategoryDto> allCategory = allCategories.stream()
					.map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
			int size = allCategory.size();

			// Log the size and one category
			logger.info("CategoryService: getAllCategories - size ::: {}", size);
			if (size > 0) {
				logger.info("CategoryService: getAllCategories - one category ::: {}", allCategory.get(0));
			}
			return allCategory;
		} catch (DataAccessException ex) {
			// Handle database-related exceptions
			logger.error("CategoryService: getAllCategories - DataAccessException ::: {}", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			logger.error("CategoryService: getAllCategories - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Fetch all categories
	 * 
	 * @return {data}
	 */
	@Override
	public List<CategoryDto> getCategoryByOptions(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save category
	 * 
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		logger.info("CategoryService: createCategory - inputCategoryDto ::: {}", categoryDto);

		Category category = this.modelMapper.map(categoryDto, Category.class);
		try {
			category.setCategory_id(UUID.randomUUID().toString().split("-")[0]);
			category.setCreated_by("Gajanan");
			category.setCreated_on(new Date());
			category.setUpdated_by("Gajanan");
			category.setUpdated_on(new Date());

			if (categoryDto.getCategoryStatus() == null || categoryDto.getCategoryStatus().isEmpty()) {
				category.setCategoryStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
				logger.info("ProductService: createProduct - applied default product status ::: ");
			} else if (categoryDto.getCategoryStatus().equalsIgnoreCase("publish")) {
				category.setCategoryStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
			} else {
				category.setCategoryStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
			}

			Category savedCategory = this.categoryRepo.save(category);
			logger.info("CategoryService: getAllCategories - savedCategory ::: {}", savedCategory);

			return this.modelMapper.map(savedCategory, CategoryDto.class);
		} catch (Exception ex) {
			logger.error("CategoryService: createCategory - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Update category
	 * 
	 * @param {CategoryDto, string}
	 * @return {data}
	 */
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto) {
		logger.info("CategoryService: updateCategory - inputCategoryDto  ::: {}", categoryDto);
		try {
			String categoryId = categoryDto.getCategory_id();
			Category category = this.categoryRepo.findById(categoryId).get();
			if (category != null) {
				logger.info("CategoryService: updateCategory - foundCategoryId ::: {}", category);

				category.setCategory_name(categoryDto.getCategory_name());
				category.setUpdated_by(categoryDto.getUpdated_by());
				category.setUpdated_on(new Date());
				if (categoryDto.getCategoryStatus().equalsIgnoreCase("publish")) {
					if (category.getCategoryStatus().equalsIgnoreCase("unpublish")) {
						List<Product> productsByCategory = productRepo.findByCategory(category);
						for (Product p : productsByCategory) {
							p.setProductStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
							logger.info("CategoryService: updateCategory - changed all the products to publish ::: ");
						}
					}
					category.setCategoryStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
				} else if (categoryDto.getCategoryStatus().equalsIgnoreCase("unpublish")) {
					if (category.getCategoryStatus().equalsIgnoreCase("publish")) {
						List<Product> productsByCategory = productRepo.findByCategory(category);
						for (Product p : productsByCategory) {
							p.setProductStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
							logger.info("CategoryService: updateCategory - changed all the products to unpublish ::: ");
						}
					}
					category.setCategoryStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
				}
				Category updateCategory = this.categoryRepo.save(category);
				logger.info("CategoryService: getAllCategories - savedCategory ::: {}", updateCategory);

				return this.modelMapper.map(updateCategory, CategoryDto.class);
			} else {
				logger.info("CategoryService: updateCategory - CategoryIdNotFound ::: {}", categoryId);
				throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(categoryId));
			}
		} catch (Exception ex) {
			logger.error("CategoryService: updateCategory - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Delete category
	 * 
	 * @param {string}
	 * @return {message}
	 */
	@Override
	public void deleteCategory(String categoryId) {
		logger.info("CategoryService: deleteCategory - inputCategoryId ::: {}", categoryId);
		try {
			Category category = this.categoryRepo.findById(categoryId).get();
			if (category != null) {
				logger.info("CategoryService: deleteCategory - foundCategoryId ::: {}", category);
				this.categoryRepo.delete(category);
			} else {
				logger.info("CategoryService: deleteCategory - CategoryIdNotFound ::: {}", category);
				throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(categoryId));
			}
		} catch (Exception ex) {
			logger.error("CategoryService: deleteCategory - error ::: {}", ex.getMessage());
			throw ex;
		}

	}

}
