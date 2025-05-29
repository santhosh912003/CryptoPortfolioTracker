package com.CryptoPortfolioTracker.service.Imp;
import java.util.*;
import com.CryptoPortfolioTracker.dto.AlertDTO;
import com.CryptoPortfolioTracker.entity.Alert;

public interface AlertInterface {
    public AlertDTO createAlerts(AlertDTO dto);
    public List<AlertDTO> getAllAlerts();
    public AlertDTO getAlertById(Long id);
    public List<AlertDTO> getMyAlerts(Long id);
    public List<AlertDTO> getTriggeredAlerts();
}
