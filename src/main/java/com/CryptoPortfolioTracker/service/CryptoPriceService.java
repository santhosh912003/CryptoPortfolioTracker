package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
<<<<<<< Updated upstream
import com.CryptoPortfolioTracker.entity.CryptPrice;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
=======
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.CryptPrice;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import jakarta.annotation.PostConstruct;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
=======
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
>>>>>>> Stashed changes
import java.util.stream.Collectors;

@Service
public class CryptoPriceService {

<<<<<<< Updated upstream
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

    
=======
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    private CryptoAssetRepository cryptoAssetRepository;

    private final Random random = new Random();

    @PostConstruct
    public void initPrices() {
        // Optional: initialize default prices here
    }

    // Add or update prices, saving quantity, buyPrice, currentValue, and profitOrLoss
    public List<CryptPrice> addOrUpdatePrices(List<CryptPrice> newPrices) {
        List<CryptPrice> savedPrices = new ArrayList<>();
        for (CryptPrice newPrice : newPrices) {
            Optional<CryptPrice> existingPriceOpt = cryptoPriceRepository.findById(newPrice.getSymbol());
            if (existingPriceOpt.isPresent()) {
                CryptPrice existingPrice = existingPriceOpt.get();
                existingPrice.setCurrentPrice(newPrice.getCurrentPrice());

                // Update quantity and buyPrice from input
                existingPrice.setQuantity(newPrice.getQuantity());
                existingPrice.setBuyPrice(newPrice.getBuyPrice());

                // Calculate currentValue and profitOrLoss
                double currentValue = existingPrice.getQuantity().doubleValue() * existingPrice.getCurrentPrice();
                existingPrice.setCurrentValue(round(currentValue, 2));

                double pnl = currentValue - (existingPrice.getQuantity().doubleValue() * existingPrice.getBuyPrice().doubleValue());
                existingPrice.setProfitOrLoss(round(pnl, 2));

                existingPrice.setTimestamp(LocalDateTime.now().format(formatter));
                savedPrices.add(cryptoPriceRepository.save(existingPrice));
            } else {
                // For new entry, set timestamp and calculate values similarly
                newPrice.setTimestamp(LocalDateTime.now().format(formatter));

                double currentValue = newPrice.getQuantity().doubleValue() * newPrice.getCurrentPrice();
                newPrice.setCurrentValue(round(currentValue, 2));

                double pnl = currentValue - (newPrice.getQuantity().doubleValue() * newPrice.getBuyPrice().doubleValue());
                newPrice.setProfitOrLoss(round(pnl, 2));

                savedPrices.add(cryptoPriceRepository.save(newPrice));
            }
        }
        return savedPrices;
    }

>>>>>>> Stashed changes
    @Scheduled(fixedRate = 30000)
    public void simulatePriceUpdates() {
        List<CryptPrice> prices = cryptoPriceRepository.findAll();

        for (CryptPrice price : prices) {
<<<<<<< Updated upstream
            double changePercent = (random.nextDouble() - 0.5) * 0.02; // -1% to +1%
            double newPrice = price.getCurrentPrice() * (1 + changePercent);
            price.setCurrentPrice(round(newPrice, 2));
            price.setTimestamp(LocalDateTime.now());
=======
            double changePercent = (random.nextDouble() - 0.5) * 0.02; // ±1%
            double newPrice = price.getCurrentPrice() * (1 + changePercent);
            price.setCurrentPrice(round(newPrice, 2));

            // Recalculate currentValue and profitOrLoss on price update
            if (price.getQuantity() != null && price.getBuyPrice() != null) {
                double currentValue = price.getQuantity().doubleValue() * price.getCurrentPrice();
                price.setCurrentValue(round(currentValue, 2));

                double pnl = currentValue - (price.getQuantity().doubleValue() * price.getBuyPrice().doubleValue());
                price.setProfitOrLoss(round(pnl, 2));
            }

            price.setTimestamp(LocalDateTime.now().format(formatter));
>>>>>>> Stashed changes
            cryptoPriceRepository.save(price);
        }
    }

<<<<<<< Updated upstream

    
=======
>>>>>>> Stashed changes
    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

<<<<<<< Updated upstream
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
=======
    public List<CryptoPriceDTO> getAllPrices() {
        return cryptoPriceRepository.findAll().stream()
                .map(p -> new CryptoPriceDTO(
                        p.getSymbol(),
                        p.getQuantity(),
                        p.getBuyPrice(),
                        p.getCurrentPrice(),
                        p.getCurrentValue(),
                        p.getProfitOrLoss(),
                        p.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    public CryptoPriceDTO getPriceBySymbol(String symbol) {
        return cryptoPriceRepository.findById(symbol)
                .map(p -> new CryptoPriceDTO(
                        p.getSymbol(),
                        p.getQuantity(),
                        p.getBuyPrice(),
                        p.getCurrentPrice(),
                        p.getCurrentValue(),
                        p.getProfitOrLoss(),
                        p.getTimestamp()
                ))
                .orElse(null);
    }

    // Keep your existing asset valuation method or remove if not needed anymore
    public List<CryptoPriceDTO> getAssetValuations(Long userId) {
        List<CryptoAsset> assets = cryptoAssetRepository.findByUserUserId(userId);
        List<CryptoPriceDTO> valuations = new ArrayList<>();

        for (CryptoAsset asset : assets) {
            String symbol = asset.getSymbol();
            Optional<CryptPrice> priceOpt = cryptoPriceRepository.findById(symbol);

            if (priceOpt.isPresent()) {
                CryptPrice price = priceOpt.get();
                BigDecimal quantity = asset.getQuantity();
                BigDecimal buyPrice = asset.getBuyPrice();
                double currentPrice = price.getCurrentPrice();
                double currentValue = quantity.doubleValue() * currentPrice;
                double pnl = currentValue - (quantity.doubleValue() * buyPrice.doubleValue());

                valuations.add(new CryptoPriceDTO(
                        symbol, quantity, buyPrice,
                        currentPrice, round(currentValue, 2), round(pnl, 2),
                        price.getTimestamp()
                ));
            }
        }

        return valuations;
    }
}
>>>>>>> Stashed changes
