package com.musala.assessment.drone.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "drone_battery_level_audit_log")
public class DroneBatteryLevelAuditLog extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false, precision = 5 , scale = 2)
    private BigDecimal batteryCapacity;

    @Column(nullable = false)
    private LocalDateTime runDate;
}
