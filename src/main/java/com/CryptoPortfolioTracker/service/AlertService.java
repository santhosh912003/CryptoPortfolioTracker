package com.CryptoPortfolioTracker.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.entity.CryptPrice;
import com.CryptoPortfolioTracker.repository.AlertRepository;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private CryptPrice cryptoPriceService;

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    @Scheduled(fixedRate = 30000) // Runs every 30 seconds
    public void checkAlerts() {
        List<Alert> alerts = alertRepository.findByTriggeredFalse();
        for (Alert alert : alerts) {
            double currentPrice = cryptoPriceService.getCurrentPrice(alert.getSymbol());

            if ((alert.getDirection().equals("above") && currentPrice >= alert.getTriggerPrice()) ||
                (alert.getDirection().equals("below") && currentPrice <= alert.getTriggerPrice())) {

                alert.setTriggered(true);
                alert.setTriggeredAt(LocalDateTime.now());
                alertRepository.save(alert);
                
                // Notify user (Email, Push notification, etc.)
                System.out.println("Alert triggered for " + alert.getSymbol() + " at price: " + currentPrice);
            }
        }
    }
}