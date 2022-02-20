package com.musala.assessment.drone.repository;

import com.musala.assessment.drone.model.DroneBatteryLevelAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneBatteryLevelAuditLogRepository extends JpaRepository<DroneBatteryLevelAuditLog, Long> {

    Optional<DroneBatteryLevelAuditLog> findBySerialNumber(String serialNumber);
}
