package com.CryptoPortfolioTracker.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CryptoPortfolioTracker.entity.CryptoPrice;

import java.util.Optional;


@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptoPrice,Long> {
    Optional<CryptoPrice> findTopBySymbolOrderByTimestampDesc(String symbol);
}
