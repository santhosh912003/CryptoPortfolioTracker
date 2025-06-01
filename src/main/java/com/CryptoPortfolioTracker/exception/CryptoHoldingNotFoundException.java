package com.CryptoPortfolioTracker.exception;

import org.springframework.security.core.parameters.P;

public class CryptoHoldingNotFoundException extends  RuntimeException{
    public CryptoHoldingNotFoundException(String err){
        super(err);
    }
}
