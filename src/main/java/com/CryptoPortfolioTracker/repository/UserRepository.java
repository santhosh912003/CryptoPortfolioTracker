package com.CryptoPortfolioTracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CryptoPortfolioTracker.entity.User;


@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

	User findByEmailIgnoreCase(String email);

}