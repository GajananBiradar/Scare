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
import com.scare.model.GST;
import com.scare.model.UnitOfMeasurment;
import com.scare.payloads.CategoryDto;
import com.scare.payloads.GSTDto;
import com.scare.payloads.UnitOfMeasurmentDto;
import com.scare.repository.GSTRepo;
import com.scare.service.GSTService;

@Service
public class GSTServiceImpl implements GSTService {

	private static final Logger logger = LoggerFactory.getLogger(GSTServiceImpl.class);

	@Autowired
	GSTRepo gstRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Fetch GST by Id
	 * 
	 * @param {string} GSTId
	 * @return {data}
	 */
	@Override
	public GSTDto getGSTById(String gstId) {
		logger.info("GSTService: getGSTId - inputGSTId ::: {}", gstId);
		try {
			GST gst = this.gstRepo.findById(gstId).get();
			if (gst == null) {
				logger.info("GSTService: getGSTId - GSTId NotFound::: {}", gstId);
				throw new ResourceNotFoundException("GST", "id: ", Integer.parseInt(gstId));
			}
			logger.info("GSTService: getGSTId - foundGSTId ::: {}", gst);

			return this.modelMapper.map(gst, GSTDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error("GSTService: getGSTId - inputGST Id - error ::: GST not found: {}", ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("GSTService: getGSTId - inputGSTId - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Fetch all GST by pagination
	 * 
	 * @param {Integer, Integer, String, String} pageNumber, pageSize, sortBy,
	 *                  sortDir
	 * @return {data}
	 */
	@Override
	public List<GSTDto> getGSTByPaginationAndFilter(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Fetch all GST
	 *
	 * @return {data}
	 */
	@Override
	public List<GSTDto> getAllGSTs() {
		logger.info("GSTService: getAllGSTs ");

		try {
			List<GST> allGSTs = this.gstRepo.findAll();
			List<GSTDto> allGST = allGSTs.stream().map((gst) -> this.modelMapper.map(gst, GSTDto.class))
					.collect(Collectors.toList());
			int size = allGST.size();

			// Log the size and one GST
			logger.info("GSTService: getAllGSTs - size ::: {}", size);
			if (size > 0) {
				logger.info("GSTService: getAllGSTs - one GST ::: {}", allGST.get(0));
			}

			return allGST;
		} catch (Exception ex) {
			logger.error("GSTService: getAllGSTs - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	@Override
	public List<GSTDto> getGSTByOptions(String option) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save GST
	 * 
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public GSTDto createGST(GSTDto gstDto) {
		logger.info("GSTService: createGST - inputGSTDto ::: {}", gstDto);

		GST gst = this.modelMapper.map(gstDto, GST.class);
		try {
			gst.setGst_id(UUID.randomUUID().toString().split("-")[0]);
			gst.setHsn_code(gstDto.getHsn_code());
			gst.setGstDesc(gstDto.getGstDesc());
			gst.setIgst(gstDto.getIgst());
			GST savedGST = this.gstRepo.save(gst);
			logger.info("GSTService: getAllGSTs - savedGST ::: {}", savedGST);

			return this.modelMapper.map(savedGST, GSTDto.class);
		} catch (Exception ex) {
			logger.error("GSTService: createGST - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Update GST
	 * 
	 * @param {GSTDto, string}
	 * @return {data}
	 */
	@Override
	public GSTDto updateGST(GSTDto gstDto) {
		logger.info("GSTService: updateGST - inputGSTDto  ::: {}", gstDto);
		try {
			String gstId = gstDto.getGst_id();
			GST gst = this.gstRepo.findById(gstId).get();
			if (gst != null) {
				logger.info("GSTService: updateGST - foundGSTId ::: {}", gst);

				gst.setHsn_code(gstDto.getHsn_code());
				gst.setGstDesc(gstDto.getGstDesc());
				gst.setIgst(gstDto.getIgst());

				GST updateGST = this.gstRepo.save(gst);
				logger.info("GSTtService: getAllGSTs - savedGST ::: {}", updateGST);

				return this.modelMapper.map(updateGST, GSTDto.class);
			} else {
				logger.info("GSTService: updateGST - GSTIdNotFound ::: {}", gstId);
				throw new ResourceNotFoundException("GST", "id: ", Integer.parseInt(gstId));
			}
		} catch (Exception ex) {
			logger.error("GSTService: updateGST - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Delete gst
	 * 
	 * @param {string}
	 * @return {message}
	 */
	@Override
	public void deleteGST(String gstId) {
		logger.info("GSTService: deleteGST - inputGSTId ::: {}", gstId);
		try {
			GST gst = this.gstRepo.findById(gstId).get();
			if (gst != null) {
				logger.info("GSTService: deleteGST - foundGSTId ::: {}", gst);
				this.gstRepo.delete(gst);
			} else {
				logger.info("GSTService: deleteGST - GSTIdNotFound ::: {}", gst);
				throw new ResourceNotFoundException("Category", "id: ", Integer.parseInt(gstId));
			}
		} catch (Exception ex) {
			logger.error("GSTService: deleteGST - error ::: {}", ex.getMessage());
			throw ex;
		}
	}
}
