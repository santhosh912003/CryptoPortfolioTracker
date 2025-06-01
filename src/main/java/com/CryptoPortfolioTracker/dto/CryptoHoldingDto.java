
package com.CryptoPortfolioTracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CryptoHoldingDto {

    private Long id;


    private Long userId;


    @Size(min = 2, max = 50, message = "Coin name must be between 2 and 50 characters")
    private String coinName;


    @Size(min = 1, max = 10, message = "Symbol must be between 1 and 10 characters")
    private String symbol;

    @Min(value = 1, message = "Quantity held must be at least 1")
    private int quantityHeld;

    @DecimalMin(value = "0.01", inclusive = true, message = "Buy price must be at least 0.01")
    private double buyPrice;

    @PastOrPresent(message = "Buy date cannot be in the future")
    private LocalDateTime buyDate;

}
