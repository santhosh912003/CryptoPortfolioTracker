package com.CryptoPortfolioTracker.repository;

import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CryptoAssetRepository extends JpaRepository<CryptoAsset, Long> {
    // Find all assets for a specific user by userId
    List<CryptoAsset> findByUserUserId(Long userId);

    // Find a specific asset by its cryptoId and userId (for update/delete security)
    Optional<CryptoAsset> findByCryptoIdAndUserUserId(Long cryptoId, Long userId);

    // Delete an asset by its cryptoId and userId
    void deleteByCryptoIdAndUserUserId(Long cryptoId, Long userId);

	
}
