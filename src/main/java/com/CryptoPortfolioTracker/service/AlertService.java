package com.CryptoPortfolioTracker.service;


import com.CryptoPortfolioTracker.dto.AlertDTO;
import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.exception.AlertNotFoundException;
import com.CryptoPortfolioTracker.repository.AlertRepository;
import com.CryptoPortfolioTracker.service.Imp.AlertInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService implements AlertInterface {
    @Autowired
    public AlertRepository alertrepo;

    @Autowired
    public CryptoPriceService cryptoPriceService;

    @Autowired
    public ModelMapper mapper;

    @Override
    public AlertDTO createAlerts(AlertDTO dto) {
        Alert alert = mapper.map(dto, Alert.class);
        alert.setStatus("PENDING");
        alertrepo.save(alert);
        return mapper.map(alert, AlertDTO.class);
    }

    @Override
    public List<AlertDTO> getAllAlerts() {
        List<Alert> arr = alertrepo.findAll();
        List<AlertDTO> res = new ArrayList<>();
        for (Alert it : arr) {
            res.add(mapper.map(it, AlertDTO.class));
        }

        return res;
    }

    @Override
    public AlertDTO getAlertById(Long id) {
        Alert alert = alertrepo.findById(id).orElseThrow(() -> new AlertNotFoundException("Alert with this id is not found"));
        return mapper.map(alert, AlertDTO.class);
    }

    @Override
    public List<AlertDTO> getMyAlerts(Long userId) {
        return alertrepo.findByUserUserId(userId).stream()
                .map(alert -> mapper.map(alert, AlertDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<AlertDTO> getTriggeredAlerts() {
        return alertrepo.findByStatus("TRIGGERED").stream()
                .map(alert -> mapper.map(alert, AlertDTO.class))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 30000)
    public void checkAlerts() {

        List<Alert> pendingAlerts = alertrepo.findByStatus("PENDING");

        for (Alert alert : pendingAlerts) {
            CryptoPriceDTO priceDTO = cryptoPriceService.getPriceBySymbol(alert.getSymbol());
            if (priceDTO == null) {
                System.out.println("Exception Encountered");
                continue;
            }
            double currentPrice = priceDTO.getCurrentPrice();
            if (currentPrice > alert.getTriggerPrice()) {
                alert.setStatus("TRIGGERED");
                alert.setTriggeredAt(LocalDateTime.now());
                alertrepo.save(alert);
                System.out.println("ALERT TRIGGERED for " + alert.getSymbol());
            }

        }
    }
}
