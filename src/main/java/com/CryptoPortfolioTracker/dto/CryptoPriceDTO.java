package com.CryptoPortfolioTracker.dto;

<<<<<<< Updated upstream
=======
import java.math.BigDecimal;
>>>>>>> Stashed changes
import java.time.LocalDateTime;

public class CryptoPriceDTO {
    private String symbol;
<<<<<<< Updated upstream
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
=======
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

>>>>>>> Stashed changes
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

<<<<<<< Updated upstream
=======
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

>>>>>>> Stashed changes
    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

<<<<<<< Updated upstream
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
=======
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
>>>>>>> Stashed changes
        this.timestamp = timestamp;
    }
}
