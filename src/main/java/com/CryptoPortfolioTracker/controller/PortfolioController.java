package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.AssetDto;
import com.CryptoPortfolioTracker.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    private static final Long TEST_USER_ID = 1L;  

    @GetMapping("/my")
    public ResponseEntity<List<AssetDto>> getUserPortfolio() {
        return ResponseEntity.ok(portfolioService.getUserPortfolio(TEST_USER_ID));
    }

    @PostMapping("/assets")
    public ResponseEntity<AssetDto> addAssetToPortfolio(@RequestBody AssetDto assetDto) {
        return ResponseEntity.ok(portfolioService.addAssetToPortfolio(TEST_USER_ID, assetDto));
    }

    @PutMapping("/assets/{id}")
    public ResponseEntity<AssetDto> updateAsset(
            @PathVariable Long id,
            @RequestBody AssetDto assetDto) {
        assetDto.setId(id);
        return ResponseEntity.ok(portfolioService.updateAsset(TEST_USER_ID, assetDto));
    }

    @DeleteMapping("/assets/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        portfolioService.deleteAsset(TEST_USER_ID, id);
        return ResponseEntity.noContent().build();
    }
}