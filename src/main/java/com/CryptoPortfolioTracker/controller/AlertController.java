package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.AlertDTO;

import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.service.AlertService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/alerts")
public class AlertController {
    @Autowired
    private AlertService service;


    @PostMapping
    public ResponseEntity<AlertDTO> createAlerts(@Valid @RequestBody AlertDTO dto){
        AlertDTO alert = service.createAlerts(dto);
        return  ResponseEntity.ok(alert);
    }

    @GetMapping
    public ResponseEntity<List<AlertDTO>> getAllAlerts(){
        List<AlertDTO> res = service.getAllAlerts();
        return ResponseEntity.ok(res);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AlertDTO> getAlertById(@Valid @PathVariable Long id){
        AlertDTO alert = service.getAlertById(id);
        return ResponseEntity.ok(alert);
    }
    @GetMapping("/my")
    public ResponseEntity<List<AlertDTO>> getMyAlerts(@RequestBody Long userId) {
        List<AlertDTO> alerts = service.getMyAlerts(userId);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/triggered")
    public ResponseEntity<List<AlertDTO>> getTriggeredAlerts() {
        List<AlertDTO> triggeredAlerts = service.getTriggeredAlerts();
        return ResponseEntity.ok(triggeredAlerts);
    }

    @GetMapping("/check")
    public void checkAlerts() {
        service.checkAlerts();
    }
}