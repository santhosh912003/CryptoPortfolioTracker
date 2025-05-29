package com.CryptoPortfolioTracker.service.Imp;


import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<User> getAllUsers();
    User getUserById(Long userId);
    UserDto createUser(UserDto user);

    UserDto UpdateUser(Long userId, UserDto user);

    String DeleteUser(Long userId);

    String LoginUser(LoginDto user);

}