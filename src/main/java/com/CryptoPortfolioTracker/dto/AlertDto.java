package com.CryptoPortfolioTracker.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDto {
    private Long id;
    private Long userId;
    private String symbol;
    private double triggerPrice;
    private String direction;     // "above" or "below"
    private String status;        // "pending", "triggered", "resolved"
    private Timestamp triggeredAt;
}
