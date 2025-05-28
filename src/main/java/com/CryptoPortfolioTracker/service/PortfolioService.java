package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.AssetDto;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
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
    private final UserRepository userRepository;

    public List<AssetDto> getUserPortfolio(Long userId) {
        return cryptoAssetRepository.findByUserUserId(userId).stream()
                .map(asset -> new AssetDto(
                        asset.getCryptoId(),
                        asset.getUser().getUserId(),
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
        asset.setUser(user);
        asset.setCoinName(assetDto.getCoinName());
        asset.setSymbol(assetDto.getSymbol());
        asset.setQuantity(assetDto.getQuantity());
        asset.setBuyPrice(assetDto.getBuyPrice());
        asset.setBuyDate(assetDto.getBuyDate());

        CryptoAsset saved = cryptoAssetRepository.save(asset);

        return new AssetDto(
            saved.getCryptoId(),
            userId,
            saved.getCoinName(),
            saved.getSymbol(),
            saved.getQuantity(),
            saved.getBuyPrice(),
            saved.getBuyDate()
        );
    }


    public AssetDto updateAsset(Long userId, AssetDto assetDto) {
        CryptoAsset asset = cryptoAssetRepository.findByCryptoIdAndUserUserId(assetDto.getCryptoId(), userId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        asset.setQuantity(assetDto.getQuantity());
        asset.setBuyPrice(assetDto.getBuyPrice());

        CryptoAsset updated = cryptoAssetRepository.save(asset);
        return new AssetDto(
                updated.getCryptoId(),
                updated.getUser().getUserId(),
                updated.getCoinName(),
                updated.getSymbol(),
                updated.getQuantity(),
                updated.getBuyPrice(),
                updated.getBuyDate());
    }

    @Transactional
    public void deleteAsset(Long userId, Long cryptoId) {
        cryptoAssetRepository.deleteByCryptoIdAndUserUserId(cryptoId, userId);
    }
}
