package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.CryptoPriceDto;
import com.CryptoPortfolioTracker.service.Imp.CryptoPriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class CryptoPriceController {

    @Autowired
    private CryptoPriceService service;

    // 🔍 Get all crypto prices
    @GetMapping
    public List<CryptoPriceDto> getAllPrices() {
        return service.getAllCryptoPrice();
    }

    // 🔍 Get crypto price by ID
    @GetMapping("/{id}")
    public CryptoPriceDto getPriceById(@PathVariable Long id) {
        return service.getCryptoPriceById(id);
    }

    // ➕ Create new crypto price entry
    @PostMapping("/add")
    public CryptoPriceDto addPrice(@Valid @RequestBody CryptoPriceDto dto) {
        return service.createCryptoPrice(dto);
    }
}
