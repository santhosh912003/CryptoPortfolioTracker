package com.CryptoPortfolioTracker.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CryptoPortfolioTracker.entity.CryptPrice;



@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptPrice,String > {






}
