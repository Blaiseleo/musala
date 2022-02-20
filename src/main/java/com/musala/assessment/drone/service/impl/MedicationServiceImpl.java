package com.musala.assessment.drone.service.impl;

import com.musala.assessment.drone.dto.MedicationItem;
import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.dto.response.DroneMedicationItemsResponse;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.Medication;
import com.musala.assessment.drone.repository.MedicationRepository;
import com.musala.assessment.drone.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public DroneMedicationItemsResponse getAllDroneMedicationItems(String serialNumber) {
        List<Medication> medications = medicationRepository.findAllByDrone_SerialNumber(serialNumber);

        Function<Medication, MedicationItem> populateMedicationItem = (medication) -> MedicationItem.builder()
                .name(medication.getName())
                .code(medication.getCode())
                .weight(medication.getWeight())
                .image(medication.getImage())
                .build();

        return DroneMedicationItemsResponse.builder()
                .serialNumber(serialNumber)
                .medicationItems(medications.stream().map(populateMedicationItem).collect(Collectors.toList())).build();
    }
}
