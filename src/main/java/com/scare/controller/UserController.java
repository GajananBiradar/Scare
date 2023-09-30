package com.scare.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.scare.exceptions.ResourceNotFoundException;
import com.scare.payloads.UserDto;
import com.scare.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/master/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/*
	 * Returns a User based on entered/selected User Id
	 * 
	 * @param {String} id the id of the User for which details are required
	 * 
	 * @return {UserDto} an object of all the details of the User
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") int uid) {
		logger.info("UserController: getUserId - inputUserId ::: {}", uid);
		try {
			UserDto userById = this.userService.getUserById(uid);
			logger.info("UserController: getUserId - foundUserId ::: {}", uid);
			return new ResponseEntity<>(userById, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("UserController: getUserId - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/*
	 * Creates and enters into DB a new User
	 * 
	 * @param{pageNumber, pageSize, sortBy, sortDir} object containing all details
	 * of the User to be entered
	 * 
	 * @return{List<UserDto>} all entries of User after successfully inserting into
	 * DB
	 */
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		List<UserDto> allUsers = this.userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);

		if (allUsers.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		logger.info("Collected all the user");
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}

	/*
	 * Creates and enters into DB a new User
	 * 
	 * @param{UserDto} object containing all details of the User to be entered
	 * 
	 * @return{UserDto} all entries of User after successfully inserting into DB
	 */
	@PostMapping("/register/{role}")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto, @PathVariable String role) {
		if (userDto == null) {
			logger.info("UserController: createUser - inputUserDto - UserDto is null ::: {}", userDto);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		logger.info("UserController: createUser - inputUserDto ::: {}", userDto);
		try {
			UserDto registeredUser = this.userService.registerNewUser(userDto, role);
			logger.info("UserController: created a User - inputUserDto ::: {}", userDto);

			return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
		} catch (Exception ex) {
			// Log the error
			logger.error("UserController: createUser - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);

		logger.info("Created new user");
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	/*
	 * Update a User by ID
	 * 
	 * @param{UserDto, UserId} object containing all the details about User and id
	 * of User
	 * 
	 * @return{UserDto} returns updated User
	 */
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") int uid) {
		logger.info("UserController: updateUser - UserDto ::: {}", userDto);

		UserDto updatedUser = this.userService.updateUser(userDto, uid);
		logger.info("UserController: updated User - UserDto ::: {}", userDto);

		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/*
	 * Delete a User by UserId
	 * 
	 * @param{UserId} takes User Id
	 * 
	 * @return{String} confirmation of successful deletion
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int uid) {
		logger.info("UserController: deleteUser - UserId ::: {}", uid);
		try {
			this.userService.deleteUser(uid);

			logger.info("UserController: deletedUser - UserId ::: {}", uid);
			return new ResponseEntity(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			logger.error("UserController: deleteUser - inputUserId - error ::: User not found: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception ex) {
			// Log the error
			logger.error("UserController: deleteUser - error ::: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
