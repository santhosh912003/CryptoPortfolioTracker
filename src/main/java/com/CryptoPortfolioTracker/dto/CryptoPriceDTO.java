package com.CryptoPortfolioTracker.dto;


import java.math.BigDecimal;

import java.time.LocalDateTime;

public class CryptoPriceDTO {
    private String symbol;


    // Getters and setters

    private BigDecimal quantity;
    private BigDecimal buyPrice;


    private double currentPrice;
    private double currentValue;
    private double profitOrLoss;
    private String timestamp;

    public CryptoPriceDTO(String symbol, BigDecimal quantity, BigDecimal buyPrice, double currentPrice, double currentValue, double profitOrLoss, String timestamp) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
        this.currentValue = currentValue;
        this.profitOrLoss = profitOrLoss;
        this.timestamp = timestamp;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getProfitOrLoss() {
        return profitOrLoss;
    }

    public void setProfitOrLoss(double profitOrLoss) {
        this.profitOrLoss = profitOrLoss;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}



