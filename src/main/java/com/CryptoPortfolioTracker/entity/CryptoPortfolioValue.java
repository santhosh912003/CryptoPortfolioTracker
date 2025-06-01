package com.CryptoPortfolioTracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="crypto_portfolio_value")
public class CryptoPortfolioValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;


    private String symbol;
    private String coinName;
    private LocalDateTime buydate;
    private double buyprice;
    private double quantityHeld;
    private double currentPrice;
    private double currentValue;
    private double pnl;

    private Timestamp createdAt;

    public void setBuyPrice(double buyPrice) {
        this.buyprice = buyPrice;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buydate = buyDate;
    }

}
