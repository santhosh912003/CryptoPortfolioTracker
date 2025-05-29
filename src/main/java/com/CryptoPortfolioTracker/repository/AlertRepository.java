package com.CryptoPortfolioTracker.repository;
import com.CryptoPortfolioTracker.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByUserUserId(Long userId);
    List<Alert> findByStatus(String status);
}