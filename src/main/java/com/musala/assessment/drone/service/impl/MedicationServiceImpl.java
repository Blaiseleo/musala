package com.musala.assessment.drone.service.impl;

import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.Medication;
import com.musala.assessment.drone.repository.MedicationRepository;
import com.musala.assessment.drone.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    @Override
    public Medication loadDrone(Drone drone, LoadDroneRequest loadDroneRequest) {
        return medicationRepository.save(Medication.builder()
                .name(loadDroneRequest.getName())
                .code(loadDroneRequest.getCode())
                .weight(loadDroneRequest.getWeight())
                .image(loadDroneRequest.getImage())
                .drone(drone)
                .build());
    }
}
