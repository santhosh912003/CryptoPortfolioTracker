package com.CryptoPortfolioTracker.service.Imp;


import com.CryptoPortfolioTracker.dto.CryptoHoldingDto;
import com.CryptoPortfolioTracker.dto.CryptoPriceDto;
import com.CryptoPortfolioTracker.entity.CryptoHolding;
import com.CryptoPortfolioTracker.entity.CryptoPrice;
import com.CryptoPortfolioTracker.exception.CryptoPriceNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import com.CryptoPortfolioTracker.service.interfaces.CryptoPriceInterface;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CryptoPriceService implements CryptoPriceInterface {
    private static final Logger logger = LoggerFactory.getLogger((CryptoPriceService.class));

    @Autowired
    public CryptoPriceRepository repo;

    @Autowired
    public ModelMapper mapper;

    @Override
    public List<CryptoPriceDto> getAllCryptoPrice() {
        logger.info("Got all crypto Prices");
        List<CryptoPrice> arr = repo.findAll();
        List<CryptoPriceDto> res = new ArrayList<>();
        for(CryptoPrice c: arr){
            res.add(mapper.map(c, CryptoPriceDto.class));
        }

        return res;
    }

    @Override
    public CryptoPriceDto getCryptoPriceById(Long id) {
        logger.info("Fetching Crypto price by ID: ");
        return mapper.map(repo.findById(id).orElseThrow(()-> new CryptoPriceNotFoundException("The crypto price for ths given id is not found")),CryptoPriceDto.class);
    }

    @Override
    public CryptoPriceDto createCryptoPrice(CryptoPriceDto dto) {
        logger.info("Creating Crypto Price");
        CryptoPrice price = mapper.map(dto, CryptoPrice.class);
        repo.save(price);
        return mapper.map(price, CryptoPriceDto.class);
    }
}