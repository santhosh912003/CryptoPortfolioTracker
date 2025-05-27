package com.CryptoPortfolioTracker.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CryptoPortfolioTracker.entity.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByTriggeredFalse();
}