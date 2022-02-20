package com.musala.assessment.drone.service.impl;

import com.musala.assessment.drone.dto.request.DroneRegistrationRequest;
import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.dto.response.DroneResponse;
import com.musala.assessment.drone.dto.response.LoadDroneResponse;
import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.exception.NotFoundException;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.Medication;
import com.musala.assessment.drone.repository.DroneRepository;
import com.musala.assessment.drone.service.DroneService;
import com.musala.assessment.drone.service.MedicationService;
import com.musala.assessment.drone.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationService medicationService;

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

    @Transactional
    @Override
    public LoadDroneResponse loadDroneWithMedicationItem(LoadDroneRequest loadDroneRequest) {
        Drone drone = droneRepository.findBySerialNumber(loadDroneRequest.getDroneSerialNumber()).orElseThrow
                (() -> new NotFoundException(String.format("Drone with serial number %s not found", loadDroneRequest.getDroneSerialNumber())));

        ValidatorUtil.validateDroneIsInIdleOrLoadingState(drone.getState());
        ValidatorUtil.validateBatteryLevel(drone.getBatteryCapacity());
        ValidatorUtil.validateDroneIsNotOverWeightLimit(drone.getWeightLimit(), drone.getCurrentWeight(), loadDroneRequest.getWeight());

        Medication medicationItem = medicationService.loadDrone(drone, loadDroneRequest);
        updateDroneDetails(drone, loadDroneRequest.getWeight());

        return buildResponse(medicationItem);
    }

    @Override
    public List<DroneResponse> getAllAvailableDronesForLoading() {
        List<Drone> drones = droneRepository.findAllByStateIn(Arrays.asList(State.IDLE, State.LOADING));

        Function<Drone, DroneResponse> getAvailableDrones = (drone) -> DroneResponse.builder()
                .serialNumber(drone.getSerialNumber())
                .model(drone.getModel())
                .batteryCapacity(drone.getBatteryCapacity())
                .weightLimit(drone.getWeightLimit())
                .currentWeight(formatCurrentWeight(drone.getCurrentWeight()))
                .state(drone.getState())
                .build();

        return drones.stream().map(getAvailableDrones).collect(Collectors.toList());
    }

    private LoadDroneResponse buildResponse(Medication medicationItem) {
        return LoadDroneResponse.builder()
                .name(medicationItem.getName())
                .code(medicationItem.getCode())
                .image(medicationItem.getImage())
                .weight(medicationItem.getWeight())
                .drone(DroneResponse.builder()
                        .serialNumber(medicationItem.getDrone().getSerialNumber())
                        .model(medicationItem.getDrone().getModel())
                        .batteryCapacity(medicationItem.getDrone().getBatteryCapacity())
                        .state(medicationItem.getDrone().getState())
                        .currentWeight(medicationItem.getDrone().getCurrentWeight())
                        .weightLimit(medicationItem.getDrone().getWeightLimit())
                        .build())
                .build();
    }

    private void updateDroneDetails(Drone drone, double weight) {
        drone.setCurrentWeight(drone.getCurrentWeight() + weight);
        updateDroneState(drone);
    }

    private Drone updateDroneState(Drone drone) {
        if (State.IDLE.equals(drone.getState())){
            drone.setState(State.LOADING);
        } else if (State.LOADING.equals(drone.getState())){
            if(drone.getWeightLimit() == drone.getCurrentWeight()){
                drone.setState(State.LOADED);
            }
        }
        return droneRepository.save(drone);
    }

    private double formatCurrentWeight(double currentWeight){
        return Math.round(currentWeight * 100.0) / 100.0;
    }

}
