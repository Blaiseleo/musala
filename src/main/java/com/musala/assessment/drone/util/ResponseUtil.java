package com.musala.assessment.drone.util;

import com.musala.assessment.drone.common.DroneServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseUtil {

    public DroneServiceResponse buildErrorResponse(HttpStatus statusCode, String message){
        return DroneServiceResponse.builder()
                .requestSuccessful(false)
                .responseCode(statusCode.value())
                .responseMessage(message)
                .build();
    }
}
