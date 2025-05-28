package com.CryptoPortfolioTracker.repository;

import com.CryptoPortfolioTracker.entity.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CryptoAssetRepository extends JpaRepository<CryptoAsset, Long> {
    List<CryptoAsset> findByUserId(Long userId);
    Optional<CryptoAsset> findByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);

}
