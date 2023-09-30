package com.scare.service;

import java.util.List;

import com.scare.payloads.UserDto;

public interface UserService {

	// Register User
	UserDto registerNewUser(UserDto user, String role);

	// Create
	UserDto createUser(UserDto userdto);

	// Update
	UserDto updateUser(UserDto userdto, Integer userId);

	// get one user
	UserDto getUserById(Integer userId);

	// get all users
	List<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// Delete
	void deleteUser(Integer userId);

}
