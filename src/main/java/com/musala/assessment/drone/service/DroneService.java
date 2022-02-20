package com.musala.assessment.drone.service;

import com.musala.assessment.drone.dto.request.DroneRegistrationRequest;
import com.musala.assessment.drone.dto.response.DroneResponse;

public interface DroneService {
    DroneResponse registerDrone(DroneRegistrationRequest registrationRequest);
}
