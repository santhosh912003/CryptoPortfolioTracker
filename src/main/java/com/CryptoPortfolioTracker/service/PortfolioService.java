package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.AssetDto;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final CryptoAssetRepository cryptoAssetRepository;

    public List<AssetDto> getUserPortfolio(Long userId) {
        return cryptoAssetRepository.findByUserId(userId).stream()
            .map(asset -> new AssetDto(
                asset.getId(),
                asset.getUserId(),
                asset.getCoinName(),
                asset.getSymbol(),
                asset.getQuantity(),
                asset.getBuyPrice(),
                asset.getBuyDate()
            ))
            .collect(Collectors.toList());
    }

    public AssetDto addAssetToPortfolio(Long userId, AssetDto assetDto) {
        CryptoAsset asset = new CryptoAsset();
        asset.setUserId(userId); 
        asset.setCoinName(assetDto.getCoinName());  
        asset.setSymbol(assetDto.getSymbol());
        asset.setQuantity(assetDto.getQuantity());
        asset.setBuyPrice(assetDto.getBuyPrice());
        asset.setBuyDate(assetDto.getBuyDate() != null ? assetDto.getBuyDate() : LocalDate.now());

        CryptoAsset savedAsset = cryptoAssetRepository.save(asset);
        assetDto.setId(savedAsset.getId());
        return assetDto;
    }

    public AssetDto updateAsset(Long userId, AssetDto assetDto) {
        CryptoAsset asset = cryptoAssetRepository.findByIdAndUserId(assetDto.getId(), userId)
            .orElseThrow(() -> new RuntimeException("Asset not found"));
            
        asset.setQuantity(assetDto.getQuantity());
        asset.setBuyPrice(assetDto.getBuyPrice());

        cryptoAssetRepository.save(asset);
        return assetDto;
    }
    @Transactional
    public void deleteAsset(Long userId, Long assetId) {
        cryptoAssetRepository.deleteByIdAndUserId(assetId, userId);
    }
}