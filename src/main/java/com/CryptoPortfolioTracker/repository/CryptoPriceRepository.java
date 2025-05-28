package com.CryptoPortfolioTracker.repository;

<<<<<<< Updated upstream
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CryptoPortfolioTracker.entity.CryptPrice;



@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptPrice,String >  {
	
	

=======
import com.CryptoPortfolioTracker.entity.CryptPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoPriceRepository extends JpaRepository<CryptPrice, String> {
>>>>>>> Stashed changes
}
