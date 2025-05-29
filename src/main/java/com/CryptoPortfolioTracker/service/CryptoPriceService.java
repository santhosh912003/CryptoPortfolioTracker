package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.CryptoPriceDTO;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.CryptPrice;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CryptoPriceService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(CryptoPriceService.class);

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
        log.info("Add or Update prices to portfolio");
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

    @Scheduled(fixedRate = 30000)
    public void simulatePriceUpdates() {
        log.info("Simulate price updates");
        List<CryptPrice> prices = cryptoPriceRepository.findAll();

        for (CryptPrice price : prices) {
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
            cryptoPriceRepository.save(price);
        }
    }

    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public List<CryptoPriceDTO> getAllPrices() {
        log.info("get all prices");
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
        log.info("get price for a single symbol crytocoin");
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