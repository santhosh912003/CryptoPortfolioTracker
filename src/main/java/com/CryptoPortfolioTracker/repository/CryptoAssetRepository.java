package com.CryptoPortfolioTracker.repository;

import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CryptoAssetRepository extends JpaRepository<CryptoAsset, Long> {

    List<CryptoAsset> findByUserUserId(Long userId);

    Optional<CryptoAsset> findByCryptoIdAndUserUserId(Long cryptoId, Long userId);

    void deleteByCryptoIdAndUserUserId(Long cryptoId, Long userId);


}
