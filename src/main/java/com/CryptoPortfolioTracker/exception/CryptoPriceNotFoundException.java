package com.CryptoPortfolioTracker.exception;

public class CryptoPriceNotFoundException extends RuntimeException {
    public CryptoPriceNotFoundException(String message) {
        super(message);
    }
}
