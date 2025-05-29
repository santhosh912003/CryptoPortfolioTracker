package com.CryptoPortfolioTracker.exception;

public class AssetNotFoundException extends RuntimeException{


    public AssetNotFoundException(String error) {
        super(error);
    }


}

