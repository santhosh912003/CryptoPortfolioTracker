package com.CryptoPortfolioTracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssetDto {
    private Long id;        
    private Long userId;     
    private String coinName;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal buyPrice;
    private LocalDate buyDate;

    public AssetDto() {
    }

    public AssetDto(Long id, Long userId, String coinName, String symbol,
                    BigDecimal quantity, BigDecimal buyPrice, LocalDate buyDate) {
        this.id = id;
        this.userId = userId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.buyDate = buyDate;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

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

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }
}
