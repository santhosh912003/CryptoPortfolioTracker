package com.CryptoPortfolioTracker.service.Imp;


import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<User> getAllUsers();
    User getUserById(Long id);
    UserDto createUser(UserDto user);

    UserDto UpdateUser(Long id, UserDto user);

    String DeleteUser(Long id);

    String LoginUser(LoginDto user);

}