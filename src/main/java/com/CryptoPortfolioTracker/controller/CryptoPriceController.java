package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
import com.CryptoPortfolioTracker.service.CryptoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prices")
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;

    @Autowired
    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    // GET /prices - get all current prices
    @GetMapping
    public ResponseEntity<List<CryptoPriceDTO>> getAllPrices() {
        List<CryptoPriceDTO> prices = cryptoPriceService.getAllPrices();
        return ResponseEntity.ok(prices);
    }

    // GET /prices/{symbol} - get price for a specific coin
    @GetMapping("/{symbol}")
    public ResponseEntity<CryptoPriceDTO> getPriceBySymbol(@PathVariable String symbol) {
        CryptoPriceDTO priceDto = cryptoPriceService.getPriceBySymbol(symbol.toUpperCase());
        if (priceDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(priceDto);
    }

    // ✅ POST /prices/simulate - simulate random price updates manually
    @PostMapping("/simulate")
    public ResponseEntity<List<CryptoPriceDTO>> simulatePriceUpdate() {
        cryptoPriceService.simulatePriceUpdates();
        List<CryptoPriceDTO> updatedPrices = cryptoPriceService.getAllPrices();
        return ResponseEntity.ok(updatedPrices);
    }
}
