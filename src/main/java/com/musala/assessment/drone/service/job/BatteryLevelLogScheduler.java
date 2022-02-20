package com.musala.assessment.drone.service.job;

import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.DroneBatteryLevelAuditLog;
import com.musala.assessment.drone.service.DroneBatteryLevelAuditLogService;
import com.musala.assessment.drone.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BatteryLevelLogScheduler {

    private final DroneService droneService;
    private final DroneBatteryLevelAuditLogService droneBatteryLevelAuditLogService;

    @Value("${battery.level.job.schedule.record.limit:100}")
    private int recordLimit;

    @Scheduled(cron = "${cron.process.job.schedule:0 * * * * ?}") //Every hour
    public void runBatteryLevelCheck() {
        log.info("Running battery level check job");

        Pageable pageRequest = PageRequest.of(0, recordLimit);

        boolean isLastPage = false;
        while (!isLastPage) {

            Page<Drone> dronesEntries = droneService.findAll(pageRequest);

            logOrUpdateBatteryLevel(dronesEntries.getContent());

            isLastPage = dronesEntries.isLast();

            if(!isLastPage){
                pageRequest = pageRequest.next();
            }
        }

        log.info("Battery level check job has ran successfully");
    }

    private void logOrUpdateBatteryLevel(List<Drone> content) {
        for(Drone drone : content){
            DroneBatteryLevelAuditLog droneBatteryLevelAuditLog = droneBatteryLevelAuditLogService.findBySerialNumber(drone.getSerialNumber()).orElseGet(
                    () -> droneBatteryLevelAuditLogService.logBatteryLevel(drone));

            droneBatteryLevelAuditLogService.updateBatteryLevel(droneBatteryLevelAuditLog, drone.getBatteryCapacity());
        }
    }
}
