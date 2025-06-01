package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.dto.UserResponseDto;
import com.CryptoPortfolioTracker.entity.Role;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.InvalidRoleException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repo;

    // Authorize only ADMIN users
    private void authorizeAdmin(String email){
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email is not found"));
        if(!"ADMIN".equals(String.valueOf(user.getRole()))){
            logger.warn("Unauthorized access attempt");
            throw new InvalidRoleException("Only ADMINs are authorized to perform this action");
        }
    }

    // Get all users - only for ADMIN
    @GetMapping()
    public List<User> getAllUsers(@RequestParam String email){
        logger.info("Fetching all users");
        authorizeAdmin(email);
        return service.getAllUsers();
    }

    // Get user by ID - only for ADMIN
    @GetMapping("/{id}")
    public User getUserById(@RequestParam String email, @PathVariable Long id){
        logger.info("Fetching user by ID");
        authorizeAdmin(email);
        return service.getUserById(id);
    }

    // Update user role - only for ADMIN
    @PutMapping("/{id}/role")
    public UserResponseDto updateUser(@RequestParam String email, @Valid @PathVariable Long id, @RequestBody UserDto user){
        logger.info("Updating user role");
        authorizeAdmin(email);
        return service.UpdateUser(id, user);
    }

    // Delete user by ID - only for ADMIN
    @DeleteMapping("/{id}")
    public String deleteUserById(@RequestParam String email, @PathVariable Long id){
        logger.info("Deleting user");
        authorizeAdmin(email);
        return service.DeleteUser(id);
    }
}
