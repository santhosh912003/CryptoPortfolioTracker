package com.CryptoPortfolioTracker.entity;

import org.springframework.stereotype.Component;

@Component
public class UserEntity {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
