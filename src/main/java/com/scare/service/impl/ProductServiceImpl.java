package com.scare.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.scare.config.AppConstants;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.exceptions.UserAccessDeniedException;
import com.scare.model.Category;
import com.scare.model.Product;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.ProductDto;
import com.scare.repository.CategoryRepo;
import com.scare.repository.ProductRepo;
import com.scare.service.ProductService;

@Service
@Component
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Fetch product by Id
	 * 
	 * @param {string} categoryId
	 * @return {data}
	 */
	@Override
	public ProductDto getProductById(String productId) {
		logger.info("ProductService: getProductId - inputProductId ::: {}", productId);

		try {
			Product product = this.productRepo.findById(productId).get();
			if (product == null) {
				logger.info("ProductService: getProductId - ProductId NotFound::: {}", productId);
				throw new ResourceNotFoundException("Product", "id: ", Integer.parseInt(productId));
			}
			logger.info("ProductService: getProductId - foundProductId ::: {}", product);

			return this.modelMapper.map(product, ProductDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error("ProductService: getProductId - inputProduct Id - error ::: Product not found: {}",
					ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("ProductService: getProductId - inputProductId - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<ProductDto> getProductsByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Fetch all Products
	 *
	 * @return {data}
	 */
	@Override
	public List<ProductDto> getAllProducts() {
		logger.info("ProductService: getAllProducts ");

		try {
			List<Product> allProducts = this.productRepo.findAll();
			List<ProductDto> allProduct = allProducts.stream()
					.map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
			int size = allProduct.size();

			// Log the size and one product
			logger.info("ProductService: getAllProducts - size ::: {}", size);
			if (size > 0) {
				logger.info("ProductService: getAllProducts - one category ::: {}", allProduct.get(0));
			}

			return allProduct;
		} catch (Exception ex) {
			logger.error("ProductService: getAllProducts - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<ProductDto> getProductByOptions(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save Product
	 * 
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public ProductDto createProduct(ProductDto productDto) {
		logger.info("ProductService: createProduct - inputProductDto ::: {}", productDto);

		Product product = this.modelMapper.map(productDto, Product.class);
		try {
			product.setProduct_id(UUID.randomUUID().toString().split("-")[0]);
			product.setCreated_by("Gajanan");
			product.setCreated_on(new Date());
			product.setUpdated_by("Gajanan");
			product.setUpdated_on(new Date());
			product.setProductTypes(productDto.getProductTypes());
			if (productDto.getProductStatus() == null || productDto.getProductStatus().isEmpty()) {
				product.setProductStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
				logger.info("ProductService: createProduct - applied default product status ::: ");
			} else if (productDto.getProductStatus().equalsIgnoreCase("publish")) {
				product.setProductStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
			} else if (productDto.getProductStatus().equalsIgnoreCase("unpublish")) {
				product.setProductStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
			}

			Category category = this.categoryRepo.findByCategory_name(productDto.getCategory_name());
//			product.setCategoryId(category.getCategory_id());
			product.setCategory_name(category.getCategory_name());
			product.setCategory(category);
			product.setReward_points(productDto.getReward_points());
			Product savedProduct = this.productRepo.save(product);
			logger.info("ProductService: createProduct - savedProduct ::: {}", savedProduct);

			return this.modelMapper.map(savedProduct, ProductDto.class);
		} catch (Exception ex) {
			logger.error("ProductService: createProduct - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Update Product
	 * 
	 * @param {ProductDto, string}
	 * @return {data}
	 */
	@Override
	public ProductDto updateProduct(ProductDto productDto) {
		logger.info("ProductService: updateProduct - inputProductDto ::: {}", productDto);
		try {
			String productId = productDto.getProduct_id();
			Product product = this.productRepo.findById(productId).get();
			if (product != null) {
				logger.info("ProductService: updateProduct - foundProductId ::: {}", product);

				Category category = this.categoryRepo.findByCategory_name(productDto.getCategory_name());

				product.setProduct_name(productDto.getProduct_name());
				product.setReward_points(productDto.getReward_points());
				product.setCategory_name(productDto.getCategory_name());
				product.setCategory(category);
				product.setProductTypes(productDto.getProductTypes());
				product.setUpdated_by("Krishna");
				product.setUpdated_on(new Date());
				if (productDto.getProductStatus().equalsIgnoreCase("publish")) {
					product.setProductStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
				} else {
					product.setProductStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
				}
				Product updateProduct = this.productRepo.save(product);
				logger.info("ProductService: updateProducts - upadtedProduct ::: {}", updateProduct);

				return this.modelMapper.map(updateProduct, ProductDto.class);
			} else {
				logger.info("ProductService: updateProduct - ProductIdNotFound ::: {}", productId);
				throw new ResourceNotFoundException("Product", "id: ", Integer.parseInt(productId));

			}
		} catch (Exception ex) {
			logger.error("ProductService: updateProduct - error ::: {}", ex.getMessage());
			throw ex;
		}

	}

	/**
	 * Delete Product
	 * 
	 * @param {string}
	 * @return {message}
	 */
	@Override
	public void deleteProduct(String productId) {
		logger.info("ProductService: deleteProduct - inputProductId ::: {}", productId);
		try {
			Product product = this.productRepo.findById(productId).get();
			if (product != null) {
				logger.info("ProductService: deleteProduct - foundProductId ::: {}", product);

				this.productRepo.delete(product);
			} else {
				logger.info("ProductService: deleteProduct - ProductIdNotFound ::: {}", product);
				throw new ResourceNotFoundException("Product", "id: ", Integer.parseInt(productId));
			}
		} catch (Exception ex) {
			logger.error("ProductService: deleteProduct - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

}
