//package com.CryptoPortfolioTracker.repository;
//
//import com.CryptoPortfolioTracker.entity.CryptoHolding;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Repository
//public interface CryptoAssetRepository extends JpaRepository<CryptoHolding, Long> {
//
//    List<CryptoHolding> findByUserUserId(Long userId);
//
//    Optional<CryptoHolding> findByCryptoIdAndUserUserId(Long cryptoId, Long userId);
//
//    void deleteByCryptoIdAndUserUserId(Long cryptoId, Long userId);
//
//
//}
