package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.CryptoPortfolioValueDto;

import com.CryptoPortfolioTracker.entity.CryptoPortfolioValue;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoHoldingsRepository;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import com.CryptoPortfolioTracker.repository.PortfolioValuationRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.CryptoPortfolioValuationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CryptoPortfolioValuationServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private CryptoHoldingsRepository holdingsRepo;

    @Mock
    private CryptoPriceRepository priceRepo;

    @Mock
    private PortfolioValuationRepository valuationRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CryptoPortfolioValuationService service;

    @Test
    void testGeneratePortfolioValuations_UserNotFound() {
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.generatePortfolioValuations(1L));
    }

    @Test
    void testGetAllPortfolioValuations() {
        when(valuationRepo.findAllByUserId(anyLong())).thenReturn(Collections.singletonList(new CryptoPortfolioValue()));
        when(mapper.map(any(CryptoPortfolioValue.class), eq(CryptoPortfolioValueDto.class))).thenReturn(new CryptoPortfolioValueDto());

        List<CryptoPortfolioValueDto> result = service.getAllPortfolioValuations(1L);

        assertEquals(1, result.size());
        verify(valuationRepo, times(1)).findAllByUserId(anyLong());
    }
}