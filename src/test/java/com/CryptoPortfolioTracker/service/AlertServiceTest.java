package com.CryptoPortfolioTracker.service;


import com.CryptoPortfolioTracker.dto.AlertDTO;
import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.exception.AlertNotFoundException;
import com.CryptoPortfolioTracker.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlertServiceTest {

    private AlertRepository alertRepository;
    private CryptoPriceService cryptoPriceService;
    private ModelMapper modelMapper;
    private AlertService alertService;

    @BeforeEach
    void setUp() {
        alertRepository = mock(AlertRepository.class);
        cryptoPriceService = mock(CryptoPriceService.class);
        modelMapper = new ModelMapper();
        alertService = new AlertService();
        alertService.alertrepo = alertRepository;
        alertService.cryptoPriceService = cryptoPriceService;
        alertService.mapper = modelMapper;
    }

    @Test
    void testCreateAlerts() {
        AlertDTO dto = new AlertDTO(null, 1L, "BTC", 30000.0, "ABOVE", null, null);
        Alert alertEntity = modelMapper.map(dto, Alert.class);
        alertEntity.setStatus("PENDING");

        when(alertRepository.save(any(Alert.class))).thenAnswer(i -> {
            Alert saved = i.getArgument(0);
            saved.setId(100L);
            return saved;
        });

        AlertDTO savedDto = alertService.createAlerts(dto);

        assertNotNull(savedDto.getId());
        assertEquals("BTC", savedDto.getSymbol());
        assertEquals("PENDING", savedDto.getStatus());
    }

    @Test
    void testGetAllAlerts() {
        Alert alert = new Alert(1L, null, "BTC", 28000.0, "ABOVE", "PENDING", null);
        when(alertRepository.findAll()).thenReturn(Collections.singletonList(alert));

        List<AlertDTO> alerts = alertService.getAllAlerts();

        assertEquals(1, alerts.size());
        assertEquals("BTC", alerts.get(0).getSymbol());
    }

    @Test
    void testGetAlertById_Found() {
        Alert alert = new Alert(1L, null, "ETH", 2000.0, "ABOVE", "PENDING", null);
        when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));

        AlertDTO dto = alertService.getAlertById(1L);

        assertEquals("ETH", dto.getSymbol());
        assertEquals(2000.0, dto.getTriggerPrice());
    }

    @Test
    void testGetAlertById_NotFound() {
        when(alertRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AlertNotFoundException.class, () -> alertService.getAlertById(1L));
    }

    @Test
    void testGetMyAlerts() {
        Alert alert1 = new Alert(1L, null, "BTC", 30000.0, "ABOVE", "PENDING", null);
        Alert alert2 = new Alert(2L, null, "ETH", 2000.0, "BELOW", "TRIGGERED", null);

        when(alertRepository.findByUserUserId(5L)).thenReturn(Arrays.asList(alert1, alert2));

        List<AlertDTO> result = alertService.getMyAlerts(5L);

        assertEquals(2, result.size());
    }

    @Test
    void testGetTriggeredAlerts() {
        Alert alert = new Alert(1L, null, "XRP", 0.5, "ABOVE", "TRIGGERED", LocalDateTime.now());
        when(alertRepository.findByStatus("TRIGGERED")).thenReturn(Collections.singletonList(alert));

        List<AlertDTO> triggeredAlerts = alertService.getTriggeredAlerts();

        assertEquals(1, triggeredAlerts.size());
        assertEquals("TRIGGERED", triggeredAlerts.get(0).getStatus());
    }




}

