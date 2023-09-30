package com.scare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scare.exceptions.ResourceNotFoundException;
import com.scare.model.User;
import com.scare.repository.UserRepo;


@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	//Loading user from database using user name
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user", "email "+username, 0));
		return user;
	}

	
	
}
