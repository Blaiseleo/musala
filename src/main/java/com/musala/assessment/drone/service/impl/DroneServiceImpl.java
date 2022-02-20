package com.musala.assessment.drone.service.impl;

import com.musala.assessment.drone.dto.request.DroneRegistrationRequest;
import com.musala.assessment.drone.dto.response.DroneResponse;
import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.repository.DroneRepository;
import com.musala.assessment.drone.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    @Override
    public DroneResponse registerDrone(DroneRegistrationRequest registrationRequest) {
        DroneResponse registrationResponse = new DroneResponse();
        Drone drone = droneRepository.save(Drone.builder()
                .serialNumber(registrationRequest.getSerialNumber())
                .model(registrationRequest.getModel())
                .weightLimit(registrationRequest.getWeightLimit())
                .currentWeight(0)
                .state(State.IDLE)
                .batteryCapacity(new BigDecimal(100))
                .build());
        BeanUtils.copyProperties(drone, registrationResponse);
        return registrationResponse;
    }
}
