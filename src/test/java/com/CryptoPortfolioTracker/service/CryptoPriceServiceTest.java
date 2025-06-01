package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.CryptoPriceDto;
import com.CryptoPortfolioTracker.entity.CryptoPrice;
import com.CryptoPortfolioTracker.exception.CryptoPriceNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import com.CryptoPortfolioTracker.service.Imp.CryptoPriceService;
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
public class CryptoPriceServiceTest {

    @Mock
    private CryptoPriceRepository repo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CryptoPriceService service;

    @Test
    void testGetAllCryptoPrice() {
        when(repo.findAll()).thenReturn(Collections.singletonList(new CryptoPrice()));
        when(mapper.map(any(CryptoPrice.class), eq(CryptoPriceDto.class))).thenReturn(new CryptoPriceDto());

        assertEquals(1, service.getAllCryptoPrice().size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetCryptoPriceById_Success() {
        CryptoPrice price = new CryptoPrice();
        when(repo.findById(anyLong())).thenReturn(Optional.of(price));
        when(mapper.map(price, CryptoPriceDto.class)).thenReturn(new CryptoPriceDto());

        assertNotNull(service.getCryptoPriceById(1L));
        verify(repo, times(1)).findById(anyLong());
    }

    @Test
    void testGetCryptoPriceById_NotFound() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CryptoPriceNotFoundException.class, () -> service.getCryptoPriceById(1L));
    }

    @Test
    void testCreateCryptoPrice() {
        CryptoPriceDto dto = new CryptoPriceDto();
        CryptoPrice price = new CryptoPrice();

        when(mapper.map(dto, CryptoPrice.class)).thenReturn(price);
        when(repo.save(price)).thenReturn(price);
        when(mapper.map(price, CryptoPriceDto.class)).thenReturn(dto);

        assertNotNull(service.createCryptoPrice(dto));
        verify(repo, times(1)).save(price);
    }
}