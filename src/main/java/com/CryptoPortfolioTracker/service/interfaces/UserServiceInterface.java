package com.CryptoPortfolioTracker.service.interfaces;


import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.dto.UserResponseDto;
import com.CryptoPortfolioTracker.entity.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> getAllUsers();
    User getUserById(Long Id);
    UserResponseDto createUser(UserDto user);

    UserResponseDto UpdateUser(Long Id, UserDto user);

    String DeleteUser(Long Id);

    String LoginUser(LoginDto user);

}