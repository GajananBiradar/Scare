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
import com.scare.model.GST;
import com.scare.model.ServiceType;
import com.scare.payloads.ServiceTypeDto;
import com.scare.repository.GSTRepo;
import com.scare.repository.ServiceTypeRepo;
import com.scare.service.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
	private static final Logger logger = LoggerFactory.getLogger(ServiceTypeServiceImpl.class);

	@Autowired
	ServiceTypeRepo serviceTypeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GSTRepo gstRepo;

	/**
	 * Fetch service type by Id
	 * 
	 * @param {string} serviceTypeId
	 * @return {data}
	 */
	@Override
	public ServiceTypeDto getServiceTypeById(String serviceTypeId) {
		logger.info("ServiceTypeService: getServiceTypeById - inputServiceTypeId ::: {}", serviceTypeId);

		try {
			ServiceType serviceType = this.serviceTypeRepo.findById(serviceTypeId).get();
			if (serviceType == null) {
				logger.info("ServiceTypeService: getServiceTypeById - ServiceTypeId NotFound::: {}", serviceTypeId);
				throw new ResourceNotFoundException("ServiceType", "id: ", Integer.parseInt(serviceTypeId));
			}
			logger.info("ServiceTypeService: getServiceTypeById - foundServiceTypeId ::: {}", serviceType);
			serviceType.setHsn_code(serviceType.getGst().getHsn_code());
			return this.modelMapper.map(serviceType, ServiceTypeDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error(
					"ServiceTypeService: getServiceTypeById - inputServiceType Id - error ::: ServiceType not found: {}",
					ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("ServiceTypeService: getServiceTypeById - inputServiceTypeId - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<ServiceTypeDto> getServiceTypeByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Fetch all service types
	 *
	 * @return {data}
	 */
	@Override
	public List<ServiceTypeDto> getAllServiceTypes() {
		logger.info("ServiceTypeService: getAllServiceTypes ");

		try {
			List<ServiceType> allServiceTypes = this.serviceTypeRepo.findAll();
			List<ServiceTypeDto> allServiceType = allServiceTypes.stream()
					.map((serviceType) -> this.modelMapper.map(serviceType, ServiceTypeDto.class))
					.collect(Collectors.toList());
			int size = allServiceType.size();

			// Log the size and one ServiceType
			logger.info("ServiceTypeService: getAllServiceTypes - size ::: {}", size);
			if (size > 0) {
				logger.info("ServiceTypeService: getAllServiceTypes - one ServiceType ::: {}", allServiceType.get(0));
			}

			return allServiceType;
		} catch (Exception ex) {
			logger.error("ServiceTypeService: getAllServiceTypes - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<ServiceTypeDto> getServiceTypeByOptions(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save ServiceType
	 * 
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public ServiceTypeDto createServiceType(ServiceTypeDto serviceTypeDto) {
		logger.info("ServiceTypeService: createServiceType - inputServiceTypeDto ::: {}", serviceTypeDto);

		ServiceType serviceType = this.modelMapper.map(serviceTypeDto, ServiceType.class);
		try {
			serviceType.setService_id(UUID.randomUUID().toString().split("-")[0]);
			serviceType.setServiceType(serviceTypeDto.getServiceType());
			serviceType.setAbbrivation(serviceTypeDto.getAbbrivation());

//			serviceType.setHsn_code(serviceTypeDto.getHsn_code());
			GST gstHsn = gstRepo.findByHsn_code(serviceTypeDto.getHsn_code());
//			GST gstHsn = gstRepo.findByHsn_code(serviceTypeDto.getGst());
			serviceType.setGst(gstHsn);

			ServiceType savedServiceType = this.serviceTypeRepo.save(serviceType);
			logger.info("ServiceTypeService: getAllServiceTypes - savedServiceType ::: {}", savedServiceType);

			return this.modelMapper.map(savedServiceType, ServiceTypeDto.class);
		} catch (Exception ex) {
			logger.error("ServiceTypeService: createServiceType - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Update service type
	 * 
	 * @param {ServiceTypeDto, string}
	 * @return {data}
	 */
	@Override
	public ServiceTypeDto updateServiceType(ServiceTypeDto serviceTypeDto) {
		logger.info("ServiceTypeService: updateServiceType - inputServiceTypeDto ::: {}", serviceTypeDto);
		try {
			String serviceTypeId = serviceTypeDto.getService_id();
			ServiceType serviceType = this.serviceTypeRepo.findById(serviceTypeId).get();

			if (serviceType != null) {
				logger.info("CategoryService: updateCategory - foundCategoryId ::: {}", serviceType);

				serviceType.setServiceType(serviceTypeDto.getServiceType());
				GST gstHsn = gstRepo.findByHsn_code(serviceTypeDto.getHsn_code());
				serviceType.setGst(gstHsn);
//				serviceType.setHsn_code(serviceTypeDto.getHsn_code());
				serviceType.setAbbrivation(serviceTypeDto.getAbbrivation());

				ServiceType updateServiceType = this.serviceTypeRepo.save(serviceType);
				logger.info("ServiceTypeService: getAllServiceTypes - savedServiceType ::: {}", updateServiceType);

				return this.modelMapper.map(updateServiceType, ServiceTypeDto.class);
			} else {
				logger.info("ServiceTypeService: updateServiceType - ServiceTypeIdNotFound ::: {}", serviceTypeId);
				throw new ResourceNotFoundException("ServiceType", "id: ", Integer.parseInt(serviceTypeId));
			}
		} catch (Exception ex) {
			logger.error("ServiceTypeService: updateServiceType - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Delete serviceType
	 * 
	 * @param {string}
	 * @return {message}
	 */
	@Override
	public void deleteServiceType(String serviceTypeId) {
		logger.info("ServiceTypeService: deleteServiceType - inputServiceTypeId ::: {}", serviceTypeId);
		try {
			ServiceType serviceType = this.serviceTypeRepo.findById(serviceTypeId).get();
			if (serviceType != null) {
				logger.info("ServiceTypeService: deleteServiceType - foundServiceTypeId ::: {}", serviceType);
				this.serviceTypeRepo.delete(serviceType);
			} else {
				logger.info("ServiceTypeService: updateServiceType - ServiceTypeIdNotFound ::: {}", serviceTypeId);
				throw new ResourceNotFoundException("ServiceType", "id: ", Integer.parseInt(serviceTypeId));
			}
		} catch (Exception ex) {
			logger.error("ServiceTypeService: deleteServiceType - error ::: {}", ex.getMessage());
			throw ex;
		}
	}
}
