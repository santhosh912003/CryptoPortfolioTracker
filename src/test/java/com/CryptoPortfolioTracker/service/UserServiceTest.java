package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.LoginDto;
import com.CryptoPortfolioTracker.dto.UserDto;
import com.CryptoPortfolioTracker.dto.UserResponseDto;
import com.CryptoPortfolioTracker.entity.Role;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository repo;
    private PasswordEncoder encoder;
    private ModelMapper mapper;
    private UserService service;

    @BeforeEach
    public void setup() {
        repo = mock(UserRepository.class);
        encoder = mock(PasswordEncoder.class);
        mapper = new ModelMapper(); // real mapper for simplicity
        service = new UserService();
        service.repo = repo;
        service.encoder = encoder;
        service.mapper = mapper;
    }

    @Test
    public void testCreateUser_success() {
        UserDto userDto = new UserDto();
        userDto.setName("John");
        userDto.setEmail("john@example.com");
        userDto.setPassword("password123");
        userDto.setRole("USER");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("John");
        savedUser.setEmail("john@example.com");
        savedUser.setRole(Role.USER);
        savedUser.setPassword("encodedPassword");

        when(encoder.encode("password123")).thenReturn("encodedPassword");
        when(repo.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto response = service.createUser(userDto);

        assertEquals("john@example.com", response.getEmail());
    }

    @Test
    public void testGetUserById_success() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");

        when(repo.findById(1L)).thenReturn(Optional.of(user));

        User result = service.getUserById(1L);
        assertEquals("Alice", result.getName());
    }

    @Test
    public void testGetUserById_notFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            service.getUserById(99L);
        });
    }

    @Test
    public void testLoginUser_success() {
        LoginDto login = new LoginDto();
        login.setEmail("john@example.com");
        login.setPassword("password123");

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");

        when(repo.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches("password123", "encodedPassword")).thenReturn(true);

        String result = service.LoginUser(login);
        assertEquals("Login Successful", result);
    }

    @Test
    public void testLoginUser_invalidPassword() {
        LoginDto login = new LoginDto();
        login.setEmail("john@example.com");
        login.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");

        when(repo.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        String result = service.LoginUser(login);
        assertEquals("Failed to login: Incorrect credentials", result);
    }
}
