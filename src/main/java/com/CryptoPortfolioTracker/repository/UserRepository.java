package com.CryptoPortfolioTracker.repository;

import com.CryptoPortfolioTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

// Create method to search for a User based on their Email
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
