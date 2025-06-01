package com.CryptoPortfolioTracker.service.interfaces;

import com.CryptoPortfolioTracker.dto.AlertDto;

import java.util.List;

public interface AlertInterface {

    // Admin-only: Create a new alert
    AlertDto createAlert(AlertDto alertDto);

    // Admin-only: Update an alert
    AlertDto updateAlert(Long alertId, AlertDto alertDto);

    // Admin-only: Delete an alert
    void deleteAlert(Long alertId);

    // Admin-only: Get all alerts (optionally filtered by status)
    List<AlertDto> getAllAlerts();

    List<AlertDto> getAllTriggeredAlerts();

    // User-only: Get alerts by user email
    List<AlertDto> getMyAlerts(String  email);
}
