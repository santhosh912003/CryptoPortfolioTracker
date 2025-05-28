package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.AssetDto;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {
	
	
	
  @Autowired
  public CryptoAssetRepository cryptoAssetRepository;
    
  @Autowired
  private final UserRepository userRepository;
  
  
  
 public List<AssetDto> getUserPortfolio(Long userId) {
        return cryptoAssetRepository.findByUserUserId(userId).stream()
            .map(asset -> new AssetDto(
                asset.getCryptoId(),                // id
                asset.getUser().getUserId(),        // userId
                asset.getCoinName(),
                asset.getSymbol(),
                asset.getQuantity(),
                asset.getBuyPrice(),
                asset.getBuyDate()))
            .collect(Collectors.toList());
    }
    

    public AssetDto addAssetToPortfolio(Long userId, AssetDto assetDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CryptoAsset asset = new CryptoAsset();
        asset.setUser(user); // Set the relationship!
        asset.setCoinName(assetDto.getCoinName());
        asset.setSymbol(assetDto.getSymbol());
        asset.setQuantity(assetDto.getQuantity());
        asset.setBuyPrice(assetDto.getBuyPrice());
        asset.setBuyDate(assetDto.getBuyDate() != null ? assetDto.getBuyDate() : LocalDate.now());

        CryptoAsset savedAsset = cryptoAssetRepository.save(asset);
        assetDto.setId(savedAsset.getCryptoId());
        return assetDto;
    }

    public AssetDto updateAsset(Long userId, AssetDto assetDto) {
        CryptoAsset asset = cryptoAssetRepository.findByCryptoIdAndUserUserId(assetDto.getId(), userId)
            .orElseThrow(() -> new RuntimeException("Asset not found"));

        asset.setQuantity(assetDto.getQuantity());
        asset.setBuyPrice(assetDto.getBuyPrice());

        cryptoAssetRepository.save(asset);
        return assetDto;
    }

    @Transactional
    public void deleteAsset(Long userId, Long assetId) {
        cryptoAssetRepository.deleteByCryptoIdAndUserUserId(assetId, userId);
    }
}
