package com.musala.assessment.drone.service;

import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.DroneBatteryLevelAuditLog;

import java.math.BigDecimal;
import java.util.Optional;

public interface DroneBatteryLevelAuditLogService {
    Optional<DroneBatteryLevelAuditLog> findBySerialNumber(String serialNumber);

    DroneBatteryLevelAuditLog logBatteryLevel(Drone drone);

    DroneBatteryLevelAuditLog updateBatteryLevel(DroneBatteryLevelAuditLog droneBatteryLevelAuditLog, BigDecimal batteryCapacity);
}
