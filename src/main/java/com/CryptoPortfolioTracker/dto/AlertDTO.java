package com.CryptoPortfolioTracker.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertDTO{
    private Long id;
    private Long userId;
    private String symbol;
    private double triggerPrice;
    private String direction;
    private String status;
    private LocalDateTime triggeredAt;
}