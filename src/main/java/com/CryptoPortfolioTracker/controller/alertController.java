package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/alerts")
public class alertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("/create")
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert, @RequestParam Long userId) {
        alert.setUserId(userId);
        return ResponseEntity.ok(alertService.createAlert(alert));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Alert>> getMyAlerts(@RequestParam Long userId) {
        return ResponseEntity.ok(alertService.getUserAlerts(userId));
    }

    @GetMapping("/triggered")
    public ResponseEntity<List<Alert>> getTriggeredAlerts() {
        return ResponseEntity.ok(alertService.getTriggeredAlerts());
    }
}
