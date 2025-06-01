package com.CryptoPortfolioTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="crypto_holdings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CryptoHolding {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String coinName;
    @Column(nullable = false)
    private String symbol;
    @Column(nullable = false)
    private int quantityHeld;
    @Column(nullable = false)
    private double buyPrice;
    @Column(nullable = false)
    private LocalDateTime buyDate;





}
