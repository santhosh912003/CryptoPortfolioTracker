package com.CryptoPortfolioTracker.entity;
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
	

}
