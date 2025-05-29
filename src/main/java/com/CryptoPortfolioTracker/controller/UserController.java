package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.InvalidRoleException;
import com.CryptoPortfolioTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping("/")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }


    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return service.getUserById(userId);
    }


    @PutMapping("/{userId}/role={userRole}")
    public UserDto UpdateUser(@PathVariable Long id, @PathVariable String UserRole, @Valid  @RequestBody UserDto user){
        if(UserRole.toUpperCase().equals("ADMIN")){
            return service.UpdateUser(id,user);
        }
        throw new InvalidRoleException("The user with the current role cannot access this endpoint");

    }


    @DeleteMapping("/{userId}")
    public String DeleteUserById(@PathVariable Long id){
        return service.DeleteUser(id);
    }


}