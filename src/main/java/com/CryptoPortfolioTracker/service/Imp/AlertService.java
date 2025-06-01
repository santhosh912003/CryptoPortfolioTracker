package com.CryptoPortfolioTracker.service.Imp;

import com.CryptoPortfolioTracker.dto.AlertDto;
import com.CryptoPortfolioTracker.entity.Alert;
import com.CryptoPortfolioTracker.entity.User;
import com.CryptoPortfolioTracker.exception.AlertNotFoundException;
import com.CryptoPortfolioTracker.exception.UserNotFoundException;
import com.CryptoPortfolioTracker.repository.AlertRepository;
import com.CryptoPortfolioTracker.repository.UserRepository;
import com.CryptoPortfolioTracker.service.interfaces.AlertInterface;
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
public class AlertService implements AlertInterface {
    private static final Logger logger=LoggerFactory.getLogger(AlertService.class);

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AlertDto createAlert(AlertDto alertDto) {
        logger.info("Creating Alert");
        User user = userRepository.findById(alertDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + alertDto.getUserId()));

        Alert alert = modelMapper.map(alertDto, Alert.class);
        alert.setUser(user);
        Alert savedAlert = alertRepository.save(alert);
        return modelMapper.map(savedAlert, AlertDto.class);
    }

    @Override
    public AlertDto updateAlert(Long alertId, AlertDto alertDto) {
        logger.info("Updating Alert");
        Optional<Alert> optionalAlert = alertRepository.findById(alertId);
        if (!optionalAlert.isPresent()) {
            throw new AlertNotFoundException("Alert not found with ID: " + alertId);
        }

        Alert alert = optionalAlert.get();
        alert.setSymbol(alertDto.getSymbol());
        alert.setTriggerPrice(alertDto.getTriggerPrice());
        alert.setDirection(alertDto.getDirection());
        alert.setStatus(alertDto.getStatus());

        if ("TRIGGERED".equalsIgnoreCase(alert.getStatus()) && alert.getTriggeredAt() == null) {
            alert.setTriggeredAt(new Timestamp(System.currentTimeMillis()));
        }

        Alert updatedAlert = alertRepository.save(alert);
        return modelMapper.map(updatedAlert, AlertDto.class);
    }

    @Override
    public void deleteAlert(Long alertId) {
        logger.info("Alert Deletion");
        if (!alertRepository.existsById(alertId)) {
            throw new AlertNotFoundException("Alert not found with ID: " + alertId);
        }
        alertRepository.deleteById(alertId);
    }

    @Override
    public List<AlertDto> getAllAlerts() {
        logger.info("Fetching all Alerts");
        List<Alert> alerts = alertRepository.findAll();
        List<AlertDto> dtos = new ArrayList<>();
        for (Alert alert : alerts) {
            dtos.add(modelMapper.map(alert, AlertDto.class));
        }
        return dtos;
    }

    @Override
    public List<AlertDto> getAllTriggeredAlerts() {
        logger.info("Fetching all triggered Alert");
        List<Alert> alerts = alertRepository.findAllByStatus("TRIGGERED");
        List<AlertDto> dtos = new ArrayList<>();
        for (Alert alert : alerts) {
            dtos.add(modelMapper.map(alert, AlertDto.class));
        }
        return dtos;
    }

    @Override
    public List<AlertDto> getMyAlerts(String email) {
        logger.info("Fetching my Alert");
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        User user = optionalUser.get();
        List<Alert> alerts = alertRepository.findAllByUser(user);
        List<AlertDto> dtos = new ArrayList<>();
        for (Alert alert : alerts) {
            dtos.add(modelMapper.map(alert, AlertDto.class));
        }
        return dtos;
    }
}
