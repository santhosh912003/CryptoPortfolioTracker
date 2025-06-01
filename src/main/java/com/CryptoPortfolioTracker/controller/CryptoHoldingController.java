package com.CryptoPortfolioTracker.controller;

import com.CryptoPortfolioTracker.dto.CryptoHoldingDto;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.InvalidRoleException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.Imp.CryptoHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class CryptoHoldingController {

    @Autowired
    private CryptoHoldingService service;

    @Autowired
    private UserRepository userRepo;

    // 🛡️ Only ADMIN allowed
    private void authorizeAdmin(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email not found"));
        if (!"ADMIN".equalsIgnoreCase(user.getRole().name())) {
            throw new InvalidRoleException("Only ADMINs are authorized to perform this action");
        }
    }

    // 🛡️ Only USER allowed
    private void authorizeUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with this email not found"));
        if (!"USER".equalsIgnoreCase(user.getRole().name())) {
            throw new InvalidRoleException("Only USERs are authorized to perform this action");
        }
    }

    // 🔍 Get all holdings (ADMIN)
    @GetMapping
    public List<CryptoHoldingDto> getAllCryptoHoldings(@RequestParam String email) {
        authorizeAdmin(email);
        return service.getAllCryptoHoldings();
    }

    // 🔍 Get by ID (ADMIN)
    @GetMapping("/{id}")
    public CryptoHoldingDto getCryptoHoldingById(@RequestParam String email, @PathVariable Long id) {
        authorizeAdmin(email);
        return service.getCryptoHoldingById(id);
    }

    @GetMapping("/my")
    public List<CryptoHoldingDto> getMyHoldings(@RequestParam String email) {
        authorizeUser(email); // Optional check
        return service.getMyCryptoHolding(email);
    }

    // ➕ Create (USER)
    @PostMapping("/add")
    public CryptoHoldingDto createCryptoHolding(@RequestParam String email, @RequestBody CryptoHoldingDto dto) {
        authorizeUser(email);
        return service.createCryptoHolding(dto);
    }

    // ✏️ Update (USER)
    @PutMapping("/update/{id}")
    public CryptoHoldingDto updateCryptoHolding(@RequestParam String email,
                                                @PathVariable Long id,
                                                @RequestBody CryptoHoldingDto dto) {
        authorizeUser(email);
        return service.updateCryptoHolding(id, dto);
    }

    // ❌ Delete (ADMIN)
    @DeleteMapping("/delete/{id}")
    public String deleteCryptoHoldingById(@RequestParam String email, @PathVariable Long id) {
        authorizeAdmin(email);
        return service.deleteCryptoHoldingById(id);
    }
}

