package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private CryptoPriceService cryptoPriceService;

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> getUserAlerts(Long userId) {
        return alertRepository.findByUserId(userId);
    }

    public List<Alert> getTriggeredAlerts() {
        return alertRepository.findAll().stream().filter(a -> "TRIGGERED".equals(a.getStatus())).collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 30000)
    public void checkAlerts() {
        List<Alert> pendingAlerts = alertRepository.findByStatus("PENDING");
        for (Alert alert : pendingAlerts) {
            double currentPrice = cryptoPriceService.getPrice(alert.getSymbol());
            if (
                    ("above".equalsIgnoreCase(alert.getDirection()) && currentPrice > alert.getTriggerPrice()) ||
                            ("below".equalsIgnoreCase(alert.getDirection()) && currentPrice < alert.getTriggerPrice())
            ) {
                alert.setStatus("TRIGGERED");
                alert.setTriggeredAt(LocalDateTime.now());
                alertRepository.save(alert);
            }
        }
    }
}
