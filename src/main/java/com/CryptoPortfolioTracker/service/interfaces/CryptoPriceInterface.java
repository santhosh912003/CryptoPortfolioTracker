package com.CryptoPortfolioTracker.service.interfaces;

import com.CryptoPortfolioTracker.dto.CryptoHoldingDto;
import com.CryptoPortfolioTracker.dto.CryptoPriceDto;
import java.util.*;


public interface CryptoPriceInterface {

    List<CryptoPriceDto> getAllCryptoPrice();
    CryptoPriceDto getCryptoPriceById(Long id);
    CryptoPriceDto createCryptoPrice(CryptoPriceDto dto);
}
