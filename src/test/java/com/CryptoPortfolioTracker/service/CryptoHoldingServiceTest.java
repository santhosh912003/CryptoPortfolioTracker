package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.CryptoHoldingDto;
import com.CryptoPortfolioTracker.entity.CryptoHolding;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.CryptoHoldingNotFoundException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoHoldingsRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.CryptoHoldingService;
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
public class CryptoHoldingServiceTest {

    @Mock
    private CryptoHoldingsRepository repo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CryptoHoldingService service;

    @Test
    void testGetAllCryptoHoldings() {
        when(repo.findAll()).thenReturn(Collections.singletonList(new CryptoHolding()));
        when(mapper.map(any(CryptoHolding.class), eq(CryptoHoldingDto.class))).thenReturn(new CryptoHoldingDto());

        assertEquals(1, service.getAllCryptoHoldings().size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetCryptoHoldingById_Success() {
        CryptoHolding holding = new CryptoHolding();
        when(repo.findById(anyLong())).thenReturn(Optional.of(holding));
        when(mapper.map(holding, CryptoHoldingDto.class)).thenReturn(new CryptoHoldingDto());

        assertNotNull(service.getCryptoHoldingById(1L));
        verify(repo, times(1)).findById(anyLong());
    }

    @Test
    void testGetCryptoHoldingById_NotFound() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CryptoHoldingNotFoundException.class, () -> service.getCryptoHoldingById(1L));
    }

    @Test
    void testCreateCryptoHolding() {
        CryptoHoldingDto dto = new CryptoHoldingDto();
        CryptoHolding holding = new CryptoHolding();

        when(mapper.map(dto, CryptoHolding.class)).thenReturn(holding);
        when(repo.save(holding)).thenReturn(holding);
        when(mapper.map(holding, CryptoHoldingDto.class)).thenReturn(dto);

        assertNotNull(service.createCryptoHolding(dto));
        verify(repo, times(1)).save(holding);
    }

    @Test
    void testUpdateCryptoHolding_UserNotFound() {
        CryptoHoldingDto dto = new CryptoHoldingDto();
        dto.setUserId(1L);
        when(repo.findById(anyLong())).thenReturn(Optional.of(new CryptoHolding()));
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.updateCryptoHolding(1L, dto));
    }

    @Test
    void testDeleteCryptoHoldingById() {
        doNothing().when(repo).deleteById(anyLong());

        assertEquals("CryptoHolding with id: 1 is deleted succesfully", service.deleteCryptoHoldingById(1L));
        verify(repo, times(1)).deleteById(anyLong());
    }
}
