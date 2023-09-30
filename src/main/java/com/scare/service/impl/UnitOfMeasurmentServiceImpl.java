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
import com.scare.model.Category;
import com.scare.model.Product;
import com.scare.model.UnitOfMeasurment;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.UnitOfMeasurmentDto;
import com.scare.repository.CategoryRepo;
import com.scare.repository.ProductRepo;
import com.scare.repository.UnitOfMeasurmentRepo;
import com.scare.service.UnitOfMeasurmentService;

@Service
public class UnitOfMeasurmentServiceImpl implements UnitOfMeasurmentService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	UnitOfMeasurmentRepo unitOfMeasurmentRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Fetch UnitOfMeasurment by Id
	 * 
	 * @param {string} UnitOfMeasurmentId
	 * @return {data}
	 */
	@Override
	public UnitOfMeasurmentDto getUnitOfMeasurmentById(String unitOfMeasurmentId) {
		logger.info("UnitOfMeasurmentService: getUnitOfMeasurmentId - inputUnitOfMeasurmentId ::: {}",
				unitOfMeasurmentId);

		try {
			UnitOfMeasurment unitOfMeasurment = this.unitOfMeasurmentRepo.findById(unitOfMeasurmentId).get();
			if (unitOfMeasurment == null) {
				logger.info("UnitOfMeasurmentService: getUnitOfMeasurmentId - UnitOfMeasurmentId NotFound::: {}",
						unitOfMeasurmentId);
				throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(unitOfMeasurmentId));
			}
			logger.info("UnitOfMeasurmentService: getUnitOfMeasurmentId - foundUnitOfMeasurmentId ::: {}",
					unitOfMeasurment);

			return this.modelMapper.map(unitOfMeasurment, UnitOfMeasurmentDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error(
					"UnitOfMeasurmentService: getUnitOfMeasurmentId - inputUnitOfMeasurment Id - error ::: UnitOfMeasurment not found: {}",
					ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("UnitOfMeasurmentService: getUnitOfMeasurmentId - inputUnitOfMeasurmentId - error ::: {}",
					ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<UnitOfMeasurmentDto> getUnitOfMeasurmentsByPaginationAndFilter(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Fetch all UnitOfMeasurment
	 *
	 * @return {data}
	 */
	@Override
	public List<UnitOfMeasurmentDto> getAllUnitOfMeasurments() {
		logger.info("UnitOfMeasurmentService: getAllUnitOfMeasurments ");

		try {
			List<UnitOfMeasurment> allUnitOfMeasurments = this.unitOfMeasurmentRepo.findAll();
			List<UnitOfMeasurmentDto> allUnitOfMeasurment = allUnitOfMeasurments.stream()
					.map((getAllUnitOfMeasurment) -> this.modelMapper.map(getAllUnitOfMeasurment, UnitOfMeasurmentDto.class))
					.collect(Collectors.toList());
			int size = allUnitOfMeasurment.size();

			// Log the size and one UnitOfMeasurment
			logger.info("UnitOfMeasurmentService: getAllUnitOfMeasurments - size ::: {}", size);
			if (size > 0) {
				logger.info("UnitOfMeasurmentService: getAllUnitOfMeasurments - one UnitOfMeasurment ::: {}",
						allUnitOfMeasurment.get(0));
			}

			return allUnitOfMeasurment;
		} catch (Exception ex) {
			logger.error("UnitOfMeasurmentService: getAllUnitOfMeasurments - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<UnitOfMeasurmentDto> getUnitOfMeasurmentByOptions(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save UnitOfMeasurment
	 * 
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public UnitOfMeasurmentDto createUnitOfMeasurment(UnitOfMeasurmentDto unitOfMeasurmentDto) {
		logger.info("UnitOfMeasurmentService: createUnitOfMeasurment - inputUnitOfMeasurmentDto ::: {}",
				unitOfMeasurmentDto);

		UnitOfMeasurment unitOfMeasurment = this.modelMapper.map(unitOfMeasurmentDto, UnitOfMeasurment.class);
		try {
			unitOfMeasurment.setUnitOfMeasurment_id(UUID.randomUUID().toString().split("-")[0]);
			unitOfMeasurment.setCreated_by("Gajanan");
			unitOfMeasurment.setCreated_on(new Date());
			unitOfMeasurment.setUpdated_by("Gajanan");
			unitOfMeasurment.setUpdated_on(new Date());
			unitOfMeasurment.setUnitOfMeasurment_Desc(unitOfMeasurmentDto.getUnitOfMeasurment_Desc());

			if (unitOfMeasurmentDto.getUnitOfMeasurmentStatus() == null
					|| unitOfMeasurmentDto.getUnitOfMeasurmentStatus().isEmpty()) {
				unitOfMeasurment.setUnitOfMeasurmentStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
				logger.info(
						"UnitOfMeasurmentService: createUnitOfMeasurment - applied default UnitOfMeasurment status ::: ");
			} else if (unitOfMeasurmentDto.getUnitOfMeasurmentStatus().equalsIgnoreCase("publish")) {
				unitOfMeasurment.setUnitOfMeasurmentStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
			} else {
				unitOfMeasurment.setUnitOfMeasurmentStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
			}

			UnitOfMeasurment savedUnitOfMeasurment = this.unitOfMeasurmentRepo.save(unitOfMeasurment);
			logger.info("UnitOfMeasurmentService: getAllUnitOfMeasurments - savedUnitOfMeasurment ::: {}",
					savedUnitOfMeasurment);

			return this.modelMapper.map(savedUnitOfMeasurment, UnitOfMeasurmentDto.class);
		} catch (Exception ex) {
			logger.error("UnitOfMeasurmentService: createUnitOfMeasurment - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Update UnitOfMeasurment
	 * 
	 * @param {UnitOfMeasurmentDto, string}
	 * @return {data}
	 */
	@Override
	public UnitOfMeasurmentDto updateUnitOfMeasurment(UnitOfMeasurmentDto unitOfMeasurmentDto) {
		logger.info("UnitOfMeasurmentService: updateUnitOfMeasurment - inputUnitOfMeasurmentDto  ::: {}", unitOfMeasurmentDto);
		try {
			String unitOfMeasurmentId = unitOfMeasurmentDto.getUnitOfMeasurment_id();
			UnitOfMeasurment unitOfMeasurment = this.unitOfMeasurmentRepo.findById(unitOfMeasurmentId).get();
			if (unitOfMeasurment != null) {
				logger.info("UnitOfMeasurmentService: updateUnitOfMeasurment - foundUnitOfMeasurmentId ::: {}",
						unitOfMeasurment);

				unitOfMeasurment.setUnitOfMeasurment_name(unitOfMeasurmentDto.getUnitOfMeasurment_name());
				unitOfMeasurment.setUnitOfMeasurment_Desc(unitOfMeasurmentDto.getUnitOfMeasurment_Desc());
				unitOfMeasurment.setUpdated_by("Gajanan");
				unitOfMeasurment.setUpdated_on(new Date());

				if (unitOfMeasurmentDto.getUnitOfMeasurmentStatus().equalsIgnoreCase("publish")) {
					unitOfMeasurment.setUnitOfMeasurmentStatus(AppConstants.PRODUCT_STATUS_PUBLISH);
				} else if (unitOfMeasurmentDto.getUnitOfMeasurmentStatus().equalsIgnoreCase("unpublish")) {
					unitOfMeasurment.setUnitOfMeasurmentStatus(AppConstants.PRODUCT_STATUS_UNPUBLISH);
				}

				UnitOfMeasurment updateUnitOfMeasurment = this.unitOfMeasurmentRepo.save(unitOfMeasurment);
				logger.info("UnitOfMeasurmentService: getAllUnitOfMeasurments - savedUnitOfMeasurment ::: {}",
						updateUnitOfMeasurment);

				return this.modelMapper.map(updateUnitOfMeasurment, UnitOfMeasurmentDto.class);
			} else {
				logger.info("UnitOfMeasurmentService: updateUnitOfMeasurment - UnitOfMeasurmentIdNotFound ::: {}",
						unitOfMeasurmentId);
				throw new ResourceNotFoundException("UnitOfMeasurment", "id: ", Integer.parseInt(unitOfMeasurmentId));
			}
		} catch (Exception ex) {
			logger.error("UnitOfMeasurmentService: updateUnitOfMeasurment - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Delete unitOfMeasurment
	 * 
	 * @param {string}
	 * @return {message}
	 */
	@Override
	public void deleteUnitOfMeasurment(String unitOfMeasurmentId) {
		logger.info("UnitOfMeasurmentService: deleteUnitOfMeasurment - inputUnitOfMeasurmentId ::: {}",
				unitOfMeasurmentId);
		try {
			UnitOfMeasurment unitOfMeasurment = this.unitOfMeasurmentRepo.findById(unitOfMeasurmentId).get();
			if (unitOfMeasurment != null) {
				logger.info("UnitOfMeasurmentService: deleteUnitOfMeasurment - foundUnitOfMeasurmentId ::: {}",
						unitOfMeasurment);
				this.unitOfMeasurmentRepo.delete(unitOfMeasurment);
			} else {
				logger.info("UnitOfMeasurmentService: deleteUnitOfMeasurment - UnitOfMeasurmentIdNotFound ::: {}",
						unitOfMeasurment);
				throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(unitOfMeasurmentId));
			}
		} catch (Exception ex) {
			logger.error("UnitOfMeasurmentService: deleteUnitOfMeasurment - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

}
