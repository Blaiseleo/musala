package com.musala.assessment.drone.service.impl;

import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.DroneBatteryLevelAuditLog;
import com.musala.assessment.drone.repository.DroneBatteryLevelAuditLogRepository;
import com.musala.assessment.drone.service.DroneBatteryLevelAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DroneBatteryLevelAuditLogServiceImpl implements DroneBatteryLevelAuditLogService {

    private final DroneBatteryLevelAuditLogRepository repository;

    @Override
    public Optional<DroneBatteryLevelAuditLog> findBySerialNumber(String serialNumber) {
        return repository.findBySerialNumber(serialNumber);
    }

    @Override
    public DroneBatteryLevelAuditLog logBatteryLevel(Drone drone) {
        return repository.save(DroneBatteryLevelAuditLog.builder()
                        .batteryCapacity(drone.getBatteryCapacity())
                        .serialNumber(drone.getSerialNumber())
                        .runDate(LocalDateTime.now())
                        .build());
    }

    @Override
    public DroneBatteryLevelAuditLog updateBatteryLevel(DroneBatteryLevelAuditLog droneBatteryLevelAuditLog, BigDecimal batteryCapacity) {
        droneBatteryLevelAuditLog.setBatteryCapacity(batteryCapacity);
        droneBatteryLevelAuditLog.setRunDate(LocalDateTime.now());
        return repository.save(droneBatteryLevelAuditLog);
    }
}
