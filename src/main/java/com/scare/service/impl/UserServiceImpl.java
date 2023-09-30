package com.scare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;

import com.scare.config.AppConstants;
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.model.Product;
import com.scare.model.Role;
import com.scare.model.User;
import com.scare.payloads.ProductDto;
import com.scare.payloads.UserDto;
import com.scare.repository.RoleRepo;
import com.scare.repository.UserRepo;
import com.scare.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final Object UserDto = null;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	/**
	 * Fetch User by Id
	 * 
	 * @param {string} userId
	 * @return {data}
	 */
	@Override
	public UserDto getUserById(Integer userId) {
		logger.info("UserService: getUserId - inputUserId ::: {}", userId);

		try {
			User user = this.userRepo.findById(userId).get();
			if (user == null) {
				logger.info("UserService: getUserId - UserId NotFound::: {}", userId);
				throw new ResourceNotFoundException("User", "id: ", userId);
			}
			logger.info("UserService: getUserId - foundUserId ::: {}", user);

			return this.modelMapper.map(user, UserDto.class);
		} catch (ResourceNotFoundException ex) {
			logger.error("UserService: getUserId - inputUser Id - error ::: User not found: {}", ex.getMessage());
			// Propagate the exception to handle NotFound in Controller
			throw ex;
		} catch (Exception ex) {
			logger.error("UserService: getUserId - inputUserId - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Fetch all Users by pagination
	 * 
	 * @param {Integer, Integer, String, String} pageNumber, pageSize, sortBy,
	 *                  sortDir
	 * @return {data}
	 */
	@Override
	public List<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		logger.info("Getting all the Users");
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<User> allUsers = this.userRepo.findAll(p);

		List<User> users = allUsers.getContent();
		logger.info("Fetched all the users in List");

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		logger.info("Converted user to userDto");

		return userDtos;
	}

	/**
	 * Save User
	 * 
	 * @param {string}
	 * @return {data}
	 */
	@Override
	public UserDto registerNewUser(UserDto userDto, String roles) {
		logger.info("UserService: createUser - inputUserDto ::: {}", userDto);

		User user = this.modelMapper.map(userDto, User.class);
		try {
			// encode the password
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			logger.info("UserService: createUser - Password encoded :::");

			Role role = null;
			// roles
			if (roles.equalsIgnoreCase("normal")) {
				role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			} else if (roles.equalsIgnoreCase("admin")) {
				role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
			}
			logger.info("UserService: createUser - Added User role :::");

			user.getRoles().add(role);
			User newUser = this.userRepo.save(user);
			logger.info("UserService: createUser - savedUser ::: {}", newUser);

			return this.modelMapper.map(newUser, UserDto.class);
		} catch (Exception ex) {
			logger.error("UserService: createUser - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * Save User
	 * 
	 * @param {userDto}
	 * @return {data}
	 */
	@Override
	public UserDto createUser(UserDto userdto) {
		logger.info("Createing user User");
		User user = this.dtoToUser(userdto);
		User savedUser = this.userRepo.save(user);
		logger.info("User is created");
		return this.userToDto(savedUser);
	}

	/**
	 * Update User
	 * 
	 * @param {UserDto, string}
	 * @return {data}
	 */
	@Override
	public UserDto updateUser(UserDto userdto, Integer userId) {
		logger.info("UserService: updateUser - inputUserDto ::: {}", userdto);
		try {
			User user = this.userRepo.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

			user.setEmail(userdto.getEmail());
			user.setPassword(userdto.getPassword());
			user.setFirstName(userdto.getFirstName());
			user.setMiddleName(userdto.getMiddleName());
			user.setLastName(userdto.getLastName());
			user.setAge(userdto.getAge());
			user.setAge(userdto.getAge());
			user.setContactNumber(userdto.getContactNumber());
			user.setGender(userdto.getGender());

			User updatedUser = this.userRepo.save(user);
			logger.info("UserService: updateUser - upadtedUser ::: {}", updatedUser);

			return this.userToDto(updatedUser);

		} catch (Exception ex) {
			logger.error("UserService: updateUser - error ::: {}", ex.getMessage());
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
	public void deleteUser(Integer userId) {
		logger.info("UserService: deleteUser - inputUserId ::: {}", userId);
		try {
			User user = this.userRepo.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
			logger.info("UserService: deleteUser - foundUserId ::: {}", user);

			this.userRepo.delete(user);
		} catch (Exception ex) {
			logger.error("UserService: deleteUser - error ::: {}", ex.getMessage());
			throw ex;
		}
	}

	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
