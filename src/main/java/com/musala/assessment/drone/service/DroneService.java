package com.musala.assessment.drone.service;

import com.musala.assessment.drone.dto.request.DroneRegistrationRequest;
import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.dto.response.DroneResponse;
import com.musala.assessment.drone.dto.response.LoadDroneResponse;

import java.util.List;

public interface DroneService {
    DroneResponse registerDrone(DroneRegistrationRequest registrationRequest);

    LoadDroneResponse loadDroneWithMedicationItem(LoadDroneRequest loadDroneRequest);

    List<DroneResponse> getAllAvailableDronesForLoading();

    DroneResponse getDroneBySerialNumber(String serialNumber);
}
