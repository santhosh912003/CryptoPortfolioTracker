package com.CryptoPortfolioTracker.repository;

import com.CryptoPortfolioTracker.entity.CryptoHolding;
import com.CryptoPortfolioTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface CryptoHoldingsRepository extends JpaRepository<CryptoHolding, Long> {
    List<CryptoHolding> findAllByUser(User user);
}
