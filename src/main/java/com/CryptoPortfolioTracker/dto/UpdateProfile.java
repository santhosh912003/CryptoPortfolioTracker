package com.CryptoPortfolioTracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfile {  // Sends request to update user profile
	
	// Update name in correct format
	@Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$")
    private String name;
	
	// Update email in correct format
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+/.[a-zA-Z]{2,}$")  
	@Email(message = "Invalid Email format")
	@NotBlank(message = "Email is required")
    private String email;

    // Update password in correct format
    @Pattern(regexp = "((?=.[A-Z])(?=.[a-z])(?=.[0-9])(?=.[@#$%!?&*]).{8,23})")   
    @NotBlank(message = "Password is required")
    private String password;
}
