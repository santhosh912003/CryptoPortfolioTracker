package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.AssetDto;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PortfolioServiceTest {

    @Test
    public void testAddAssetToPortfolio_validUser() {
        // Mock repositories
        CryptoAssetRepository cryptoAssetRepository = mock(CryptoAssetRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        // Create the service
        PortfolioService service = new PortfolioService(cryptoAssetRepository, userRepository);

        // Create input AssetDto (userId passed separately)
        AssetDto assetDto = new AssetDto();
        assetDto.setCoinName("Bitcoin");
        assetDto.setSymbol("BTC");
        assetDto.setQuantity(new BigDecimal("1.0"));
        assetDto.setBuyPrice(new BigDecimal("30000"));
        assetDto.setBuyDate(LocalDate.of(2024, 5, 10));

        Long userId = 1L;

        // Mock user returned from DB
        User user = new User();
        user.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock save behavior
        when(cryptoAssetRepository.save(any(CryptoAsset.class))).thenAnswer(invocation -> {
            CryptoAsset a = invocation.getArgument(0);
            a.setCryptoId(100L); // Simulate DB assigning ID
            return a;
        });

        // Act
        AssetDto result = service.addAssetToPortfolio(userId, assetDto);

        // Assert
        assertEquals("Bitcoin", result.getCoinName());
        assertEquals("BTC", result.getSymbol());
        assertEquals(new BigDecimal("1.0"), result.getQuantity());
        assertEquals(new BigDecimal("30000"), result.getBuyPrice());
        assertEquals(LocalDate.of(2024, 5, 10), result.getBuyDate());
        assertEquals(userId, result.getUserId());
    }
}
