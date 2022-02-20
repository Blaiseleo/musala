package com.musala.assessment.drone.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneServiceResponse {
    private boolean requestSuccessful;
    private int responseCode;
    private String responseMessage;
}
