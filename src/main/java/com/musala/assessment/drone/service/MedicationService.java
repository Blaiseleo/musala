package com.musala.assessment.drone.service;

import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.Medication;

public interface MedicationService {
    Medication loadDrone(Drone drone, LoadDroneRequest loadDroneRequest);
}
