package com.CryptoPortfolioTracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {  // Sends request for login purposes
	
	// Should enter correct email
	@Email(message = "Invalid Email format")
	@NotBlank(message = "Email is required")
	private String email;
	
	// Should enter correct password
	@NotBlank(message = "Password is required")
	private String password;
}
