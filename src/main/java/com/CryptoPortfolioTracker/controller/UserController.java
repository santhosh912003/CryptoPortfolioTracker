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


    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }


    @PutMapping("/{id}/role={userRole}")
    public UserDto UpdateUser(@PathVariable Long id, @PathVariable String UserRole, @Valid  @RequestBody UserDto user){
        if(UserRole.toUpperCase().equals("ADMINI")){
            return service.UpdateUser(id,user);
        }
        throw new InvalidRoleException("The user with the current role cannot access this endpoint");

    }


    @DeleteMapping("/{id}")
    public String DeleteUserById(@PathVariable Long id){
        return service.DeleteUser(id);
    }


}