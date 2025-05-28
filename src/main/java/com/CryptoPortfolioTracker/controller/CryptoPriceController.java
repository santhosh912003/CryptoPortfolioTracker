package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
<<<<<<< Updated upstream
=======
import com.CryptoPortfolioTracker.entity.CryptPrice;
>>>>>>> Stashed changes
import com.CryptoPortfolioTracker.service.CryptoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
<<<<<<< Updated upstream
@RequestMapping("/prices")
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;

    @Autowired
    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    // GET /prices - get all current prices
=======
@RequestMapping("/api/prices")
public class CryptoPriceController {

    @Autowired
    private CryptoPriceService cryptoPriceService;

    // ✅ Get all crypto prices with buyPrice, quantity, currentValue, profitOrLoss
>>>>>>> Stashed changes
    @GetMapping
    public ResponseEntity<List<CryptoPriceDTO>> getAllPrices() {
        List<CryptoPriceDTO> prices = cryptoPriceService.getAllPrices();
        return ResponseEntity.ok(prices);
    }

<<<<<<< Updated upstream
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
=======
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
>>>>>>> Stashed changes
    }
}
