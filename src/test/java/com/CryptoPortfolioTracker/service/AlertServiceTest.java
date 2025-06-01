package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.AlertDto;
import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.AlertNotFoundException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.AlertRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.AlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AlertService service;

    @Test
    void testCreateAlert_UserNotFound() {
        AlertDto alertDto = new AlertDto();
        alertDto.setUserId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.createAlert(alertDto));
    }

    @Test
    void testUpdateAlert_AlertNotFound() {
        when(alertRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AlertNotFoundException.class, () -> service.updateAlert(1L, new AlertDto()));
    }

    @Test
    void testGetAllAlerts() {
        when(alertRepository.findAll()).thenReturn(Collections.singletonList(new Alert()));
        when(modelMapper.map(any(Alert.class), eq(AlertDto.class))).thenReturn(new AlertDto());

        assertEquals(1, service.getAllAlerts().size());
        verify(alertRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAlert_AlertNotFound() {
        when(alertRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(AlertNotFoundException.class, () -> service.deleteAlert(1L));
    }

    @Test
    void testDeleteAlert_Success() {
        when(alertRepository.existsById(anyLong())).thenReturn(true);

        service.deleteAlert(1L);
        verify(alertRepository, times(1)).deleteById(1L);
    }
}