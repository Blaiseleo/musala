package com.musala.assessment.drone.util;

import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.exception.BatteryLevelException;
import com.musala.assessment.drone.exception.DroneStateException;
import com.musala.assessment.drone.exception.OverWeightException;

import java.math.BigDecimal;

public class ValidatorUtil {
    public static boolean validateDroneIsNotOverWeightLimit(double weightLimit, double currentWeight, double newWeight){
        if(weightLimit >= (currentWeight + newWeight)){
            return true;
        }
        throw new OverWeightException(String.format("Drone weight limit exceeded, weight limit is %s", weightLimit));
    }

    public static boolean validateBatteryLevel(BigDecimal batteryLevel){
        if(BigDecimal.valueOf(25L).compareTo(batteryLevel) <= 0){
            return true;
        }
        throw new BatteryLevelException("Battery level is currently lower than 25%");
    }

    public static void validateDroneIsInIdleOrLoadingState(State state) {
        if(!(State.IDLE.equals(state) || State.LOADING.equals(state))){
            throw new DroneStateException("Drone not in idle or loading state");
        }
    }
}
