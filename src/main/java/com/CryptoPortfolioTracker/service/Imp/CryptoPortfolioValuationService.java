package com.CryptoPortfolioTracker.service.Imp;

import com.CryptoPortfolioTracker.dto.CryptoPortfolioValueDto;
import com.CryptoPortfolioTracker.entity.CryptoHolding;
import com.CryptoPortfolioTracker.entity.CryptoPortfolioValue;
import com.CryptoPortfolioTracker.entity.CryptoPrice;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoHoldingsRepository;
import com.CryptoPortfolioTracker.repository.CryptoPriceRepository;
import com.CryptoPortfolioTracker.repository.PortfolioValuationRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.interfaces.CryptoPortfolioValueInterface;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoPortfolioValuationService implements CryptoPortfolioValueInterface {
    private static final Logger logger= LoggerFactory.getLogger(CryptoPortfolioValuationService.class);

    @Autowired
    private UserRepository user_repo;
    @Autowired
    private CryptoHoldingsRepository ch_repo;

    @Autowired
    private CryptoPriceRepository cp_repo;

    @Autowired
    private PortfolioValuationRepository pv_repo;

    @Autowired
    private ModelMapper mapper;


    @Override
    public void generatePortfolioValuations(Long id) {
        logger.info("Generating Portfolio Values");

        User user = user_repo.findById(id).orElseThrow(()->new UserNotFoundException("User with this Id is Not Found"));
        List<CryptoHolding> holdings = ch_repo.findAllByUser(user);

        for (CryptoHolding cp : holdings) {
            Optional<CryptoPrice> priceOpt = cp_repo.findTopBySymbolOrderByTimestampDesc(cp.getSymbol());

            if (priceOpt.isPresent()) {
                CryptoPrice price = priceOpt.get();

                double currentValue = cp.getQuantityHeld() * price.getCurrentPrice();
                double buyTotal = cp.getQuantityHeld() * cp.getBuyPrice();
                double pnl = currentValue - buyTotal;

                CryptoPortfolioValue valuation = new CryptoPortfolioValue();

                valuation.setUser(user);
                valuation.setSymbol(cp.getSymbol());
                valuation.setCoinName(cp.getCoinName());
                valuation.setBuyDate(cp.getBuyDate());
                valuation.setBuyPrice(cp.getBuyPrice());
                valuation.setQuantityHeld(cp.getQuantityHeld());
                valuation.setCurrentPrice(price.getCurrentPrice());
                valuation.setCurrentValue(currentValue);
                valuation.setPnl(pnl);
                valuation.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                pv_repo.save(valuation);
            }
        }
    }

    @Override
    public List<CryptoPortfolioValueDto> getAllPortfolioValuations(Long id) {
        logger.info("Fetching Portfolio Valuations");
        List<CryptoPortfolioValue> arr = pv_repo.findAllByUserId(id);
        List<CryptoPortfolioValueDto> res = new ArrayList<>();
        for (CryptoPortfolioValue c : arr) {
            res.add(mapper.map(c, CryptoPortfolioValueDto.class));
        }
        return res;
    }
}
