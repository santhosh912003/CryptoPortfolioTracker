package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.AlertDto;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.InvalidRoleException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private UserRepository userRepo;

    // Authorization method that throws if not admin
    private void authorizeAdmin(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email not found"));
        if (!"ADMIN".equalsIgnoreCase(user.getRole().name())) {
            throw new InvalidRoleException("Only ADMINs are authorized to perform this action");
        }
    }

    // Admin-only: create alert
    @PostMapping("/create")
    public ResponseEntity<?> createAlert(@RequestParam String email, @RequestBody AlertDto alertDto) {
        authorizeAdmin(email);
        AlertDto created = alertService.createAlert(alertDto);
        return ResponseEntity.ok(created);
    }

    // User-only: get alerts for given user email
    @GetMapping("/my")
    public ResponseEntity<?> getMyAlerts(@RequestParam String email) {
        List<AlertDto> alerts = alertService.getMyAlerts(email);
        return ResponseEntity.ok(alerts);
    }

    // Admin-only: get all triggered alerts
    @GetMapping("/triggered")
    public ResponseEntity<?> getTriggeredAlerts(@RequestParam String email) {
        authorizeAdmin(email);
        List<AlertDto> triggered = alertService.getAllTriggeredAlerts();
        return ResponseEntity.ok(triggered);
    }

    // Admin-only: update alert
    @PutMapping("/update/{alertId}")
    public ResponseEntity<?> updateAlert(@RequestParam String email, @PathVariable Long alertId, @RequestBody AlertDto alertDto) {
        authorizeAdmin(email);
        AlertDto updated = alertService.updateAlert(alertId, alertDto);
        return ResponseEntity.ok(updated);
    }

    // Admin-only: delete alert
    @DeleteMapping("/delete/{alertId}")
    public ResponseEntity<?> deleteAlert(@RequestParam String email, @PathVariable Long alertId) {
        authorizeAdmin(email);
        alertService.deleteAlert(alertId);
        return ResponseEntity.ok("Alert deleted successfully.");
    }
}
