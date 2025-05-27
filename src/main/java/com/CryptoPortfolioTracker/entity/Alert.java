package com.CryptoPortfolioTracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "Alerts")
public class Alert {
    // Getters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    private String symbol;
    @Setter
    private double triggerPrice;
    @Setter
    private String direction; // "above" or "below"
    @Setter
    private boolean status = false;
    @Setter
    private LocalDateTime triggeredAt;

    // Default constructor
    public Alert() {}

    // Parameterized constructor
    public Alert(User user, String symbol, double triggerPrice, String direction) {
        this.user = user;
        this.symbol = symbol;
        this.triggerPrice = triggerPrice;
        this.direction = direction;
        this.status = false;
        this.triggeredAt = null;
    }

}