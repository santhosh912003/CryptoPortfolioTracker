package com.CryptoPortfolioTracker.entity;
<<<<<<< Updated upstream
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "Crypto-Price")
public class CryptPrice {
	@Id
	@Setter
	private String symbol;
	@Setter
	private double currentPrice;
	@Setter
	private String timestamp;
	
	//Parameterized constructor
	public CryptPrice(String symbol,double currentPrice)
	{
		this.symbol=symbol;
		this.currentPrice=currentPrice;
		this.timestamp=LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
		
	}
	//Getters and Setters
	public String symbol()
	{
		return symbol;
	}
	public double currentPrice()
	{
		return currentPrice;
	}
	public String timestamp()
	{
		return timestamp;
	}
	

=======

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "crypto_price")  // Use underscores for standard naming
public class CryptPrice {

    @Id
    private String symbol;

    private double currentPrice;
    private String timestamp;
    private double currentValue;
    private double profitOrLoss;

    private BigDecimal quantity;
    private BigDecimal buyPrice;

    public CryptPrice() {

    }

    public CryptPrice(String symbol, double currentPrice, String timestamp, double currentValue, double profitOrLoss, BigDecimal quantity, BigDecimal buyPrice) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.timestamp = timestamp;
        this.currentValue = currentValue;
        this.profitOrLoss = profitOrLoss;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
    }

    public CryptPrice(String symbol, double initialPrice, LocalDateTime now) {
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "CryptPrice{" +
                "symbol='" + symbol + '\'' +
                ", currentPrice=" + currentPrice +
                ", timestamp='" + timestamp + '\'' +
                ",currentValue"+ currentValue+
                "profitOrLoss"+profitOrLoss+
                "quantity"+quantity+
                "buyPrice"+buyPrice+
                '}';
    }
>>>>>>> Stashed changes
}
