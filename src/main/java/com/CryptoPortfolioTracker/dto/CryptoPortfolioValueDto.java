package com.CryptoPortfolioTracker.dto;

import com.CryptoPortfolioTracker.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CryptoPortfolioValueDto {

    private  Long id;
    private Long userId;
    private String symbol;
    private String coinName;
    private LocalDateTime buyDate;
    private double buyPrice;
    private double quantityHeld;
    private double currentPrice;
    private double currentValue;
    private double pnl;

    private Timestamp createdAt;


}