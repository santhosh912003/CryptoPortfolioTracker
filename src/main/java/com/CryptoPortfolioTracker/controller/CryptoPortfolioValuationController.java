package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.CryptoPortfolioValueDto;
import com.CryptoPortfolioTracker.service.Imp.CryptoPortfolioValuationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valuation")
public class CryptoPortfolioValuationController {


    @Autowired
    private CryptoPortfolioValuationService valuationService;

    @PostMapping("/generate/{id}")
    public ResponseEntity<String> generatePortfolioValuation(@PathVariable Long id) {
        valuationService.generatePortfolioValuations(id);
        return ResponseEntity.ok("Portfolio valuation generated and saved.");
    }

    @GetMapping("/{id}")
    public List<CryptoPortfolioValueDto> getMyPortfolio(@PathVariable Long id){
        return valuationService.getAllPortfolioValuations(id);
    }


}
