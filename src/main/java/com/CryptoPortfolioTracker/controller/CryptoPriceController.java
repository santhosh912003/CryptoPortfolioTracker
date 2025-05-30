package com.CryptoPortfolioTracker.controller;


import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
import com.CryptoPortfolioTracker.entity.CryptPrice;
import com.CryptoPortfolioTracker.service.CryptoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class CryptoPriceController {

    @Autowired
    private CryptoPriceService cryptoPriceService;

    // ✅ Get all crypto prices with buyPrice, quantity, currentValue, profitOrLoss
    @GetMapping
    public ResponseEntity<List<CryptoPriceDTO>> getAllPrices() {
        List<CryptoPriceDTO> prices = cryptoPriceService.getAllPrices();
        return ResponseEntity.ok(prices);
    }

    // ✅ Get a specific crypto price by symbol
    @GetMapping("/{symbol}")
    public ResponseEntity<CryptoPriceDTO> getPriceBySymbol(@PathVariable String symbol) {
        CryptoPriceDTO priceDTO = cryptoPriceService.getPriceBySymbol(symbol);
        if (priceDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(priceDTO);
    }

    // ✅ Get asset valuation for a user (optional, useful if you still use CryptoAsset)
    @GetMapping("/valuations/{userId}")
    public ResponseEntity<List<CryptoPriceDTO>> getAssetValuations(@PathVariable Long userId) {
        List<CryptoPriceDTO> valuations = cryptoPriceService.getAssetValuations(userId);
        return ResponseEntity.ok(valuations);
    }

    // ✅ Add or update crypto prices (with buyPrice, quantity, etc. included)
    @PostMapping
    public ResponseEntity<List<CryptPrice>> addOrUpdatePrices(@RequestBody List<CryptPrice> prices) {
        List<CryptPrice> savedPrices = cryptoPriceService.addOrUpdatePrices(prices);
        return ResponseEntity.ok(savedPrices);
    }
}
