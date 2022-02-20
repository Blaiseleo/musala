package com.musala.assessment.drone.controller;

import com.musala.assessment.drone.dto.request.DroneRegistrationRequest;
import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.dto.response.DroneMedicationItemsResponse;
import com.musala.assessment.drone.dto.response.DroneResponse;
import com.musala.assessment.drone.dto.response.LoadDroneResponse;
import com.musala.assessment.drone.service.DroneService;
import com.musala.assessment.drone.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drone")
@RequiredArgsConstructor
public class DroneController {

    private final DroneService droneService;
    private final MedicationService medicationService;

    @PostMapping("/register")
    public DroneResponse registerDrone(@Validated @RequestBody DroneRegistrationRequest registrationRequest){
        return droneService.registerDrone(registrationRequest);
    }

    @PostMapping("/load-drone")
    public LoadDroneResponse loadDroneWithMedicationItem(@Validated @RequestBody LoadDroneRequest loadDroneRequest){
        return droneService.loadDroneWithMedicationItem(loadDroneRequest);
    }

    @GetMapping("/get-all-medication-items")
    public DroneMedicationItemsResponse getAllDroneMedicationItems(@RequestParam String serialNumber){
        return medicationService.getAllDroneMedicationItems(serialNumber);
    }
}
