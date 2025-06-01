package com.CryptoPortfolioTracker.service.Imp;

import com.CryptoPortfolioTracker.dto.CryptoHoldingDto;
import com.CryptoPortfolioTracker.entity.CryptoHolding;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.CryptoHoldingNotFoundException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoHoldingsRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.interfaces.CryptoHoldingInterface;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CryptoHoldingService implements CryptoHoldingInterface {
    private static final Logger logger= LoggerFactory.getLogger(CryptoHoldingService.class);


    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CryptoHoldingsRepository repo;

    @Autowired
    private UserRepository user_repo;

    @Override
    public List<CryptoHoldingDto> getAllCryptoHoldings() {
        logger.info("Fetching all crypto Holdings");
        List<CryptoHolding> holding = repo.findAll();
        List<CryptoHoldingDto> res = new ArrayList<>();
        for(CryptoHolding c: holding){
            res.add(mapper.map(c,CryptoHoldingDto.class));
        }

        return res;
    };
    @Override
    public CryptoHoldingDto getCryptoHoldingById(Long id){
        logger.info("Fetching Crypto Holding by Id");
        return mapper.map(repo.findById(id).orElseThrow(()-> new CryptoHoldingNotFoundException("The cryptoHolding for this id is not Found")), CryptoHoldingDto.class);
    }
    @Override
    public CryptoHoldingDto createCryptoHolding(CryptoHoldingDto dto){
        logger.info("Creating CryptoHoldings");
        CryptoHolding crypto = mapper.map(dto, CryptoHolding.class);
        repo.save(crypto);
        return mapper.map(crypto, CryptoHoldingDto.class);
    }
    @Override
    public CryptoHoldingDto updateCryptoHolding(Long id, CryptoHoldingDto dto){
        logger.info("Updating the Crypto Holdings");
        CryptoHolding existing = repo.findById(id).orElseThrow(()->new CryptoHoldingNotFoundException("CryptoHoldings for this id is not found"));
        if (dto.getUserId() != null) {
            User user = user_repo.findById(dto.getUserId()).orElseThrow(()->new UserNotFoundException("User with this id is not found"));
            existing.setUser(user);
        }

        if (dto.getCoinName() != null && !dto.getCoinName().isBlank()) {
            existing.setCoinName(dto.getCoinName());
        }

        if (dto.getSymbol() != null && !dto.getSymbol().isBlank()) {
            existing.setSymbol(dto.getSymbol());
        }

        if (dto.getQuantityHeld() > 0) {
            existing.setQuantityHeld(dto.getQuantityHeld());
        }

        if (dto.getBuyPrice() > 0) {
            existing.setBuyPrice(dto.getBuyPrice());
        }

        if (dto.getBuyDate() != null) {
            existing.setBuyDate(dto.getBuyDate());
        }

        CryptoHolding saved = repo.save(existing);
        return mapper.map(saved, CryptoHoldingDto.class);
    }

    @Override
    public String deleteCryptoHoldingById(Long id) {
        repo.deleteById(id);
        return "CryptoHolding with id: "+id+" is deleted successfully";

    }



    @Override
    public List<CryptoHoldingDto> getMyCryptoHolding(String email){
        logger.info("Fetching Crypto Holdings");
        User user = user_repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email is not found"));

        List<CryptoHolding> holdings = repo.findAllByUser(user);
        List<CryptoHoldingDto> result = new ArrayList<>();

        for (CryptoHolding holding : holdings) {
            result.add(mapper.map(holding, CryptoHoldingDto.class));
        }

        return result;

    }

}
