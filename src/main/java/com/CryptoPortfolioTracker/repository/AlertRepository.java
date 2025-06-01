package com.CryptoPortfolioTracker.repository;

import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findAllByUser(User user);

    List<Alert> findAllByStatus(String status);  // For triggered alerts, e.g., status = "triggered"

    List<Alert> findAllByUserAndStatus(User user, String status);
}
