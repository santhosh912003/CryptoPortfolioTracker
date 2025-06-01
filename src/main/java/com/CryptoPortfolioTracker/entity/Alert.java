package com.CryptoPortfolioTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String symbol; // e.g., BTC, ETH

    @Column(nullable = false)
    private double triggerPrice;

    @Column(nullable = false)
    private String direction; // "above" or "below"

    @Column(nullable = false)
    private String status; // "pending", "triggered", "resolved"

    private Timestamp triggeredAt;
}
