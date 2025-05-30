package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.entity.Role;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.InvalidRoleException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder encoder;


    @Override
    public List<User> getAllUsers() {
        log.info("Get all the users");
        return repo.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        log.info("Get user by UserId");
        return repo.findById(userId).orElseThrow(()-> new UserNotFoundException("The user with this id: "+ userId +" not found"));
    }



    @Override
    public UserDto createUser(UserDto user) {
        log.info("Create user");
        User newuser = mapper.map(user, User.class);


        newuser.setPassword(encoder.encode(user.getPassword()));
        repo.save(newuser);
        return mapper.map(newuser, UserDto.class);

    }

    @Override
    public UserDto UpdateUser(Long userId, UserDto user) {
        log.info("Update existing user");
        User existing = repo.findById(userId).orElseThrow(()-> new UserNotFoundException("User with this id: "+userId+" not found"));
        try{
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());
            existing.setPassword(encoder.encode(user.getPassword()));

            existing.setRole(Role.valueOf(user.getRole().toUpperCase()));
        }
        catch(IllegalArgumentException ex){
            throw new InvalidRoleException("Invalid role: "+ user.getRole());
        }

        repo.save(existing);
        return mapper.map(existing, UserDto.class);
    }

    @Override
    public String DeleteUser(Long userId) {
        log.info("Delete the existing user");
        try{
            repo.deleteById(userId);
            return "Deleted the User with id: "+ userId+" Successfully";
        }
        catch(UserNotFoundException e){
            throw new UserNotFoundException("The user is not created successfully..");
        }
    }



    @Override
    public String LoginUser(LoginDto user) {
        log.info("Enter login credentials Email and Password");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());

        if(user==null){
            return "Login DTo mapping is null";
        }

        if (!user.getEmail().matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
            return "Invalid email format";
        }
        if (user.getPassword() == null) {
            return "Password should not be null";
        }
        if (user.getPassword().length() < 6) {
            return "Password must be at least 8 characters";
        }

        String email = user.getEmail().trim();
        User existingUser = repo.findByEmailIgnoreCase(email);

        if (existingUser == null) {
            return "User not found";
        }

        if (encoder.matches(user.getPassword(), existingUser.getPassword())) {
            return "Login Successful";
        }

        return "Wrong Password";
    }

}