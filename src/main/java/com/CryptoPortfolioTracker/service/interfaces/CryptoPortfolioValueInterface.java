package com.CryptoPortfolioTracker.service.interfaces;

import com.CryptoPortfolioTracker.dto.CryptoPortfolioValueDto;
import com.CryptoPortfolioTracker.entity.CryptoPortfolioValue;

import java.util.List;

public interface CryptoPortfolioValueInterface {
    void generatePortfolioValuations(Long id);

    List<CryptoPortfolioValueDto> getAllPortfolioValuations(Long id);
}
