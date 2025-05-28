package com.CryptoPortfolioTracker.dto;

import java.time.LocalDateTime;

public class CryptoPriceDTO {
    private String symbol;
    private double currentPrice;
    private LocalDateTime timestamp; 

    public CryptoPriceDTO() {
    }

    public CryptoPriceDTO(String symbol, double currentPrice, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
