package com.CryptoPortfolioTracker.controller.auth;


import com.CryptoPortfolioTracker.dto.UserResponseDto;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.service.Imp.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;


import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@RequestMapping("api/auth")
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private UserService service;

	// This handles user registration
	@PostMapping("/register")
	public UserResponseDto HandleRegistration(@Valid @RequestBody UserDto user){
		logger.info("Received registration request");
		UserResponseDto response = service.createUser(user);
		logger.info("User registration completed");
		return response;
	}

	// This handles user login
	@PostMapping("/login")
	public String HandleLogin(@RequestBody LoginDto user){
		logger.info("Received login request");
		String response = service.LoginUser(user);
		logger.info("User login processed");
		return response;
	}
}