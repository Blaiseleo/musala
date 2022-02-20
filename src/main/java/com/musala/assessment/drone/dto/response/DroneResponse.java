package com.musala.assessment.drone.dto.response;

import com.musala.assessment.drone.enums.Model;
import com.musala.assessment.drone.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneResponse {

    private String serialNumber;

    private Model model;

    private double weightLimit;

    private double currentWeight;

    private BigDecimal batteryCapacity;

    private State state;
}
