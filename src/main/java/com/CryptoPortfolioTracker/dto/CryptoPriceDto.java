package com.CryptoPortfolioTracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPriceDto {

    private Long id;

    @NotBlank(message = "Symbol is required")
    @Size(max = 10, message = "Symbol must be at most 10 characters")
    private String symbol;

    @Positive(message = "Current price must be positive")
    private double currentPrice;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
}
