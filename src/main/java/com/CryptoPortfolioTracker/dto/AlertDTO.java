package com.CryptoPortfolioTracker.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDTO{
	private Long id;
	private Long UserId;
	private String symbol;
	private double triggerPrice;
	private String direction;
	private boolean status;
	private LocalDateTime triggeredAt;
}