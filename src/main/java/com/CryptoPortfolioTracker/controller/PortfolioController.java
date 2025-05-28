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

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<AssetDto>> getUserPortfolio(@PathVariable Long userId) {
        return ResponseEntity.ok(portfolioService.getUserPortfolio(userId));
    }

    @PostMapping("/assets")
    public ResponseEntity<AssetDto> addAssetToPortfolio(@RequestBody AssetDto assetDto) {
        return ResponseEntity.ok(portfolioService.addAssetToPortfolio(assetDto.getUserId(), assetDto));
    }

    @PutMapping("/assets/{cryptoId}")
    public ResponseEntity<AssetDto> updateAsset(@PathVariable Long cryptoId, @RequestBody AssetDto assetDto) {
        assetDto.setCryptoId(cryptoId);
        return ResponseEntity.ok(portfolioService.updateAsset(assetDto.getUserId(), assetDto));
    }

    @DeleteMapping("/assets/{userId}/{cryptoId}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long userId, @PathVariable Long cryptoId) {
        portfolioService.deleteAsset(userId, cryptoId);
        return ResponseEntity.noContent().build();
    }
}
