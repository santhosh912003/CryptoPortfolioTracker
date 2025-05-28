package com.CryptoPortfolioTracker.repository;


import com.CryptoPortfolioTracker.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUserId(Long userId);
    List<Alert> findByStatus(String status);
}
