package com.CryptoPortfolioTracker.dto;

import com.CryptoPortfolioTracker.entity.Role;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	// Should have 4-16 Characters including Alphanumeric values
	@Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$")
    private String name;
    
	// Should be created as per proper email convention 
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+/.[a-zA-Z]{2,}$")  
	@Email(message = "Invalid Email format")
	@NotBlank(message = "Email is required")
    private String email;
    
    // Should have combination of Alphanumeric values and Special Symbols while having at least 1 Upper case, 1 Lower case, 1 Number and 1 Special Symbols. Should have at least 8 characters, up to 23 characters.
    @Pattern(regexp = "((?=.[A-Z])(?=.[a-z])(?=.[0-9])(?=.[@#$%!?&*]).{8,23})")   
    private String password;
    
    // Necessary to pick a role to move forward
    @NotNull(message = "Role is required")
    private Role roles;
    
}
