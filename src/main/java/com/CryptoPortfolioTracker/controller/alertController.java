package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.AlertDTO;
import com.CryptoPortfolioTracker.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class alertController {

    private final AlertService alertService;

    @PostMapping("/create")
    public ResponseEntity<String> createAlert(@RequestBody AlertDTO alertDto) {
        AlertService.createAlert(alertDto);
        return ResponseEntity.ok("Alert created successfully.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AlertDTO>> getUserAlerts(@PathVariable Long userId) {
        List<AlertDTO> alerts = AlertService.getAlertsByUserId(userId);
        return ResponseEntity.ok(alerts);
        
    }

    @GetMapping("/user/{userId}/triggered")
    public ResponseEntity<List<AlertDTO>> getTriggeredAlerts(@PathVariable Long userId) {
        List<AlertDTO> triggeredAlerts = AlertService.getAlertsByUserIdAndStatus(userId, true);
        return ResponseEntity.ok(triggeredAlerts);
    }

    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<AlertDTO>> getPendingAlerts(@PathVariable Long userId) {
        List<AlertDTO> pendingAlerts = AlertService.getAlertsByUserIdAndStatus(userId, false);
        return ResponseEntity.ok(pendingAlerts);
    }
}
