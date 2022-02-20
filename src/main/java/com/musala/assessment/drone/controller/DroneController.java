package com.musala.assessment.drone.controller;

import com.musala.assessment.drone.dto.request.DroneRegistrationRequest;
import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.dto.response.DroneResponse;
import com.musala.assessment.drone.dto.response.LoadDroneResponse;
import com.musala.assessment.drone.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drone")
@RequiredArgsConstructor
public class DroneController {

    private final DroneService droneService;

    @PostMapping("/register")
    public DroneResponse registerDrone(@Validated @RequestBody DroneRegistrationRequest registrationRequest){
        return droneService.registerDrone(registrationRequest);
    }

    @PostMapping("/load-drone")
    public LoadDroneResponse loadDroneWithMedicationItem(@Validated @RequestBody LoadDroneRequest loadDroneRequest){
        return droneService.loadDroneWithMedicationItem(loadDroneRequest);
    }
}
