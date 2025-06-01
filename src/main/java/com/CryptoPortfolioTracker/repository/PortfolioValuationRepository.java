package com.CryptoPortfolioTracker.repository;

import com.CryptoPortfolioTracker.entity.CryptoPortfolioValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface PortfolioValuationRepository extends JpaRepository<CryptoPortfolioValue, Long> {
    List<CryptoPortfolioValue> findAllByUserId(Long userId);
}
