package com.CryptoPortfolioTracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


public class AssetDto {

    private Long cryptoId;
    private Long userId;
    private String coinName;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal buyPrice;
    private LocalDate buyDate;

    // No-arg constructor
    public AssetDto() {}

    // All-arg constructor
    public AssetDto(Long cryptoId, Long userId, String coinName, String symbol,
                    BigDecimal quantity, BigDecimal buyPrice, LocalDate buyDate) {
        this.cryptoId = cryptoId;
        this.userId = userId;
        this.coinName = coinName;
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.buyDate = buyDate;
    }

    public Long getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Long cryptoId) {
        this.cryptoId = cryptoId;
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
