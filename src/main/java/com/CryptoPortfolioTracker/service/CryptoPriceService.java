package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
import com.CryptoPortfolioTracker.entity.CryptPrice;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CryptoPriceService {

    private final CryptoPriceRepository cryptoPriceRepository;
    private final Random random = new Random();

    // Supported coins with initial prices
    private final String[] symbols = {"BTC", "ETH", "SOL", "ADA"};
    private final double[] initialPrices = {30000.0, 2000.0, 35.0, 1.2};

    @Autowired
    public CryptoPriceService(CryptoPriceRepository cryptoPriceRepository) {
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    
    @PostConstruct
    public void initPrices() {
        for (int i = 0; i < symbols.length; i++) {
            if (!cryptoPriceRepository.existsById(symbols[i])) {
                CryptPrice price = new CryptPrice(symbols[i], initialPrices[i], LocalDateTime.now());
                cryptoPriceRepository.save(price);
            }
        }
    }

    
    @Scheduled(fixedRate = 30000)
    public void simulatePriceUpdates() {
        List<CryptPrice> prices = cryptoPriceRepository.findAll();

        for (CryptPrice price : prices) {
            double changePercent = (random.nextDouble() - 0.5) * 0.02; // -1% to +1%
            double newPrice = price.getCurrentPrice() * (1 + changePercent);
            price.setCurrentPrice(round(newPrice, 2));
            price.setTimestamp(LocalDateTime.now());
            cryptoPriceRepository.save(price);
        }
    }


    
    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    // Get all current prices 
    public List<CryptoPriceDTO> getAllPrices() {
        return cryptoPriceRepository.findAll().stream()
        		.map(p -> new CryptoPriceDTO(p.getSymbol(), p.getCurrentPrice(), p.getTimestamp()))
                .collect(Collectors.toList());
        
    }

    // Get price 
    public CryptoPriceDTO getPriceBySymbol(String symbol) {
    	return cryptoPriceRepository.findById(symbol)
    			.map(p -> new CryptoPriceDTO(p.getSymbol(), p.getCurrentPrice(), p.getTimestamp()))
                .orElse(null);
    }
    public CryptoPriceDTO addPrice(CryptoPriceDTO cryptoPriceDTO) {
        
        return cryptoPriceDTO;
    }

}