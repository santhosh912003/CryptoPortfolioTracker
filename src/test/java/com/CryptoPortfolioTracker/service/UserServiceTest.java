package com.CryptoPortfolioTracker.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.repository.UserRepository;

class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder encoder;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setEmail("lavanya04@gmail.com");
        user.setPassword("encodedPwd");
    }

    @Test
    void testRegisterUser_Success() {
        UserDto dto = new UserDto();
        dto.setEmail("lavanya04@gmail.com");
        dto.setPassword("password123");

        when(repo.findByEmailIgnoreCase("lavanya04@gmail.com")).thenReturn(null);
        when(encoder.encode("password123")).thenReturn("encodedPwd");


    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        UserDto dto = new UserDto();
        dto.setEmail("lavanya04@gmail.com");

        when(repo.findByEmailIgnoreCase("lavanya04@gmail.com")).thenReturn(user);


    }

    @Test
    void testLoginUser_Success() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("lavanya04@gmail.com");
        loginDto.setPassword("password123");

        when(repo.findByEmailIgnoreCase("lavanya04@gmail.com")).thenReturn(user);
        when(encoder.matches("password123", "encodedPwd")).thenReturn(true);

        String result = service.LoginUser(loginDto);

        assertEquals("Login Successful", result);
    }

    @Test
    void testLoginUser_InvalidEmailFormat() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("invalid-email");
        loginDto.setPassword("password123");

        String result = service.LoginUser(loginDto);

        assertEquals("Invalid email format", result);
    }

    @Test
    void testLoginUser_UserNotFound() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("notfound@gmail.com");
        loginDto.setPassword("Password@123");

        when(repo.findByEmailIgnoreCase("notfound@gmail.com")).thenReturn(null);

        String result = service.LoginUser(loginDto);

        assertEquals("User not found", result);
    }

    @Test
    void testLoginUser_WrongPassword() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("lavanya04@gmail.com");
        loginDto.setPassword("wrong@Password1");

        when(repo.findByEmailIgnoreCase("lavanya04@gmail.com")).thenReturn(user);
        when(encoder.matches("wrongPassword", "encodedPwd")).thenReturn(false);

        String result = service.LoginUser(loginDto);

        assertEquals("Wrong Password", result);
    }
}
