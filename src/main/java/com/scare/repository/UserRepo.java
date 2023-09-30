package com.scare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scare.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
