package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
import com.CryptoPortfolioTracker.entity.CryptPrice;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CryptoPriceServiceTest {

    @Mock
    private CryptoPriceRepository cryptoPriceRepository;

    @Mock
    private CryptoAssetRepository cryptoAssetRepository;

    @InjectMocks
    private CryptoPriceService cryptoPriceService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdatePrices_NewAndExisting() {
        // Existing price in repo
        CryptPrice existing = new CryptPrice();
        existing.setSymbol("BTC");
        existing.setCurrentPrice(30000);
        existing.setQuantity(BigDecimal.valueOf(1));
        existing.setBuyPrice(BigDecimal.valueOf(25000));

        CryptPrice inputExisting = new CryptPrice();
        inputExisting.setSymbol("BTC");
        inputExisting.setCurrentPrice(32000);
        inputExisting.setQuantity(BigDecimal.valueOf(1.5));
        inputExisting.setBuyPrice(BigDecimal.valueOf(26000));

        CryptPrice inputNew = new CryptPrice();
        inputNew.setSymbol("ETH");
        inputNew.setCurrentPrice(2000);
        inputNew.setQuantity(BigDecimal.valueOf(2));
        inputNew.setBuyPrice(BigDecimal.valueOf(1800));

        when(cryptoPriceRepository.findById("BTC")).thenReturn(Optional.of(existing));
        when(cryptoPriceRepository.findById("ETH")).thenReturn(Optional.empty());
        when(cryptoPriceRepository.save(any(CryptPrice.class))).thenAnswer(i -> i.getArguments()[0]);

        List<CryptPrice> result = cryptoPriceService.addOrUpdatePrices(Arrays.asList(inputExisting, inputNew));

        assertEquals(2, result.size());

        CryptPrice updatedBtc = result.stream().filter(p -> "BTC".equals(p.getSymbol())).findFirst().orElse(null);
        assertNotNull(updatedBtc);
        assertEquals(32000, updatedBtc.getCurrentPrice());
        assertEquals(BigDecimal.valueOf(1.5), updatedBtc.getQuantity());
        assertTrue(updatedBtc.getCurrentValue() > 0);
        assertTrue(updatedBtc.getProfitOrLoss() != 0);
        assertNotNull(updatedBtc.getTimestamp());

        CryptPrice newEth = result.stream().filter(p -> "ETH".equals(p.getSymbol())).findFirst().orElse(null);
        assertNotNull(newEth);
        assertEquals(2000, newEth.getCurrentPrice());
        assertEquals(BigDecimal.valueOf(2), newEth.getQuantity());
        assertTrue(newEth.getCurrentValue() > 0);
        assertTrue(newEth.getProfitOrLoss() != 0);
        assertNotNull(newEth.getTimestamp());
    }

    @Test
    void testGetAllPrices() {
        CryptPrice price = new CryptPrice("BTC", 30000, "2025-05-29 12:00:00", 45000, 5000,
                BigDecimal.valueOf(1.5), BigDecimal.valueOf(25000));
        when(cryptoPriceRepository.findAll()).thenReturn(Collections.singletonList(price));

        List<CryptoPriceDTO> dtos = cryptoPriceService.getAllPrices();

        assertEquals(1, dtos.size());
        assertEquals("BTC", dtos.get(0).getSymbol());
        assertEquals(1.5, dtos.get(0).getQuantity().doubleValue());
        assertEquals(30000, dtos.get(0).getCurrentPrice());
    }

    @Test
    void testGetPriceBySymbol_Found() {
        CryptPrice price = new CryptPrice("BTC", 30000, "2025-05-29 12:00:00", 45000, 5000,
                BigDecimal.valueOf(1.5), BigDecimal.valueOf(25000));
        when(cryptoPriceRepository.findById("BTC")).thenReturn(Optional.of(price));

        CryptoPriceDTO dto = cryptoPriceService.getPriceBySymbol("BTC");

        assertNotNull(dto);
        assertEquals("BTC", dto.getSymbol());
        assertEquals(30000, dto.getCurrentPrice());
    }

    @Test
    void testGetPriceBySymbol_NotFound() {
        when(cryptoPriceRepository.findById("ETH")).thenReturn(Optional.empty());

        CryptoPriceDTO dto = cryptoPriceService.getPriceBySymbol("ETH");
        assertNull(dto);
    }

    @Test
    void testGetAssetValuations() {
        CryptoAsset asset = new CryptoAsset();
        asset.setSymbol("BTC");
        asset.setQuantity(BigDecimal.valueOf(2));
        asset.setBuyPrice(BigDecimal.valueOf(25000));
        asset.setUser(null); // user is irrelevant for this test

        CryptPrice price = new CryptPrice("BTC", 30000, "2025-05-29 12:00:00", 60000, 10000,
                BigDecimal.valueOf(2), BigDecimal.valueOf(25000));

        when(cryptoAssetRepository.findByUserUserId(1L)).thenReturn(Collections.singletonList(asset));
        when(cryptoPriceRepository.findById("BTC")).thenReturn(Optional.of(price));

        List<CryptoPriceDTO> valuations = cryptoPriceService.getAssetValuations(1L);

        assertEquals(1, valuations.size());
        CryptoPriceDTO dto = valuations.get(0);
        assertEquals("BTC", dto.getSymbol());
        assertEquals(2, dto.getQuantity().doubleValue());
        assertEquals(30000, dto.getCurrentPrice());
        assertEquals(60000, dto.getCurrentValue());
        assertEquals(10000, dto.getProfitOrLoss());
    }
}