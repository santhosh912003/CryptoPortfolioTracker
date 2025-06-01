package com.CryptoPortfolioTracker.service.interfaces;

import com.CryptoPortfolioTracker.dto.CryptoHoldingDto;
import java.util.*;

public interface CryptoHoldingInterface {
    List<CryptoHoldingDto> getAllCryptoHoldings();
    CryptoHoldingDto getCryptoHoldingById(Long id);
    CryptoHoldingDto createCryptoHolding(CryptoHoldingDto dto);
    CryptoHoldingDto updateCryptoHolding(Long id, CryptoHoldingDto dto);
    String deleteCryptoHoldingById(Long id);
    List<CryptoHoldingDto> getMyCryptoHolding(String email);

}
