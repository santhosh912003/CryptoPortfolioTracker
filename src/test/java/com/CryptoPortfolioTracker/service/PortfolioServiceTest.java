package com.CryptoPortfolioTracker.service;


import com.CryptoPortfolioTracker.dto.AssetDto;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.AssetNotFoundException;
import com.CryptoPortfolioTracker.exception.CryptoProfileNotFoundException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PortfolioServiceTest {

    private CryptoAssetRepository cryptoAssetRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        cryptoAssetRepository = mock(CryptoAssetRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        portfolioService = new PortfolioService();
        portfolioService.mapper = modelMapper;
        portfolioService.cryptoAssetRepository = cryptoAssetRepository;
        portfolioService.userRepository = userRepository;
    }

    @Test
    void testGetUserPortfolio_Success() {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        CryptoAsset asset = new CryptoAsset();
        asset.setCryptoId(10L);
        asset.setUser(user);
        asset.setSymbol("BTC");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cryptoAssetRepository.findById(userId)).thenReturn(Optional.of(asset));

        AssetDto result = portfolioService.getUserPortfolio(userId);

        assertEquals("BTC", result.getSymbol());
    }

    @Test
    void testGetUserPortfolio_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> portfolioService.getUserPortfolio(1L));
    }

    @Test
    void testGetUserPortfolio_AssetNotFound() {
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoAssetRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AssetNotFoundException.class, () -> portfolioService.getUserPortfolio(1L));
    }

    @Test
    void testAddAssetToPortfolio() {
        AssetDto dto = new AssetDto();
        dto.setCoinName("Ethereum");
        dto.setSymbol("ETH");
        dto.setBuyPrice(BigDecimal.valueOf(1000.0));
        dto.setQuantity(BigDecimal.valueOf(2.0));
        dto.setBuyDate(LocalDate.now());

        CryptoAsset asset = modelMapper.map(dto, CryptoAsset.class);

        when(cryptoAssetRepository.save(any())).thenReturn(asset);

        AssetDto result = portfolioService.addAssetToPortfolio(dto);
        assertEquals("ETH", result.getSymbol());
        assertEquals("Ethereum", result.getCoinName());
    }

    @Test
    void testUpdateAsset_Success() {
        Long assetId = 100L;
        Long userId = 1L;

        AssetDto dto = new AssetDto();
        dto.setUserId(userId);
        dto.setCryptoId(assetId);
        dto.setCoinName("Bitcoin");
        dto.setSymbol("BTC");
        dto.setQuantity(BigDecimal.valueOf(1.5));
        dto.setBuyPrice(BigDecimal.valueOf(50000.0));
        dto.setBuyDate(LocalDate.now());

        User user = new User();
        user.setUserId(userId);

        CryptoAsset existing = new CryptoAsset();
        existing.setCryptoId(assetId);
        existing.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cryptoAssetRepository.findById(assetId)).thenReturn(Optional.of(existing));
        when(cryptoAssetRepository.save(any())).thenReturn(existing);

        AssetDto result = portfolioService.updateAsset(assetId, dto);
        assertEquals("BTC", result.getSymbol());
        assertEquals("Bitcoin", result.getCoinName());
    }

    @Test
    void testUpdateAsset_UserNotFound() {
        AssetDto dto = new AssetDto();
        dto.setUserId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> portfolioService.updateAsset(1L, dto));
    }

    @Test
    void testUpdateAsset_AssetNotFound() {
        AssetDto dto = new AssetDto();
        dto.setUserId(1L);
        dto.setCryptoId(1L);

        User user = new User();
        user.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cryptoAssetRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CryptoProfileNotFoundException.class, () -> portfolioService.updateAsset(1L, dto));
    }

    @Test
    void testDeleteAsset() {
        Long userId = 1L;
        Long cryptoId = 100L;
        doNothing().when(cryptoAssetRepository).deleteByCryptoIdAndUserUserId(cryptoId, userId);
        portfolioService.deleteAsset(userId, cryptoId);
        verify(cryptoAssetRepository, times(1)).deleteByCryptoIdAndUserUserId(cryptoId, userId);
    }
}

