package com.CryptoPortfolioTracker.controller.auth;


import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.repository.UserRepository;

import jakarta.validation.Valid;
/**
 * 
 */

@RestController
@RequestMapping("api/auth")
public class RegisterController {

//	@PostMapping("/login")
//	public String HandleLogin(@Valid @RequestBody LoginDto user) {
//		User ExistingUser = userRepo.findByEmail(user.getEmail());
//		if(ExistingUser==null) {
//			return "User Not found";
//		}
//
//		if(encoder.matches(user.getPassword(),ExistingUser.getPassword())) {
//			return "Login SucessFull";
//		}
//
//		return "Failded to login";
//	}

	@Autowired
	private UserService service;


	@PostMapping("/register")
	public UserDto HandleRegistration( @Valid @RequestBody UserDto user){
		return service.createUser(user);
	}


	@PostMapping("/login")
	public String HandleLogin(@RequestBody LoginDto user){
		return service.LoginUser(user);
	}


	
	
	
	

}