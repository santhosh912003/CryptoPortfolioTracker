package com.CryptoPortfolioTracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetDto {
	
private Long id;
private Long userId;
private String coinName;
private String symbol;
private BigDecimal quantity;
private BigDecimal buyPrice;
private LocalDate buyDate;


}