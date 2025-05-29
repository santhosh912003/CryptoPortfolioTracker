package com.CryptoPortfolioTracker.service;

import com.CryptoPortfolioTracker.dto.AssetDto;
import com.CryptoPortfolioTracker.entity.CryptoAsset;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.AssetNotFoundException;
import com.CryptoPortfolioTracker.exception.CryptoProfileNotFoundException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.CryptoAssetRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j

@Service
public class PortfolioService {

    @Autowired
    public CryptoAssetRepository cryptoAssetRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ModelMapper mapper;

    /**
     * Retrieves the crypto asset portfolio of a user by their userId.
     * Throws UserNotFoundException if the user does not exist.
     * Throws AssetNotFoundException if the associated crypto asset is not found.
     * Maps the CryptoAsset entity to AssetDto and returns it.
     */

    public AssetDto getUserPortfolio(Long userId) {
        log.info("Got User Portfolio");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User is not found"));
        CryptoAsset obj = cryptoAssetRepository.findById(user.getUserId()).orElseThrow(() -> new AssetNotFoundException("Assest for this given user is not found"));
        return mapper.map(obj, AssetDto.class);

    }

    /**
     * Adds a new crypto asset to the user's portfolio.
     * Converts the incoming AssetDto to CryptoAsset entity, saves it, and maps it back to AssetDto.
     */
    public AssetDto addAssetToPortfolio(AssetDto assetDto) {
        log.info("Added Asset to the Portfolio");
        CryptoAsset obj = mapper.map(assetDto, CryptoAsset.class);
        cryptoAssetRepository.save(obj);
        return mapper.map(obj, AssetDto.class);
    }

    /**
     * Updates an existing crypto asset in the portfolio using the given AssetDto.
     * Verifies user existence and checks that the asset exists.
     * Updates relevant fields of the CryptoAsset and saves it.
     * Returns the updated asset mapped to AssetDto.
     */
    public AssetDto updateAsset(Long Id, AssetDto dto) {
        log.info("Update the Existing asset");
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        CryptoAsset asset = cryptoAssetRepository.findById(dto.getCryptoId()).orElseThrow(() -> new CryptoProfileNotFoundException("Crypto assets in this user id is not found ") );
        asset.setBuyPrice(dto.getBuyPrice());
        asset.setUser(user);
        asset.setQuantity(dto.getQuantity());
        asset.setSymbol(dto.getSymbol());
        asset.setBuyDate(dto.getBuyDate());
        asset.setCoinName(dto.getCoinName());
        asset.setBuyPrice(dto.getBuyPrice());

        cryptoAssetRepository.save(asset);
        return mapper.map(asset, AssetDto.class);
    }

    /**
     * Deletes a specific crypto asset from a user's portfolio based on userId and cryptoId.
     * This operation is transactional to ensure atomicity.
     */
    @Transactional
    public void deleteAsset(Long userId, Long cryptoId) {
        log.info("Delete the asset");
        cryptoAssetRepository.deleteByCryptoIdAndUserUserId(cryptoId, userId);
    }

}
