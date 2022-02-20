package com.musala.assessment.drone;

import com.musala.assessment.drone.enums.Model;
import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.repository.DroneRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetDroneBatteryLevelControllerTest extends BaseIntegrationTest{

    @Autowired
    private DroneRepository droneRepository;

    private static final String SERIAL_NUMBER = RandomStringUtils.randomAlphabetic(10);

    @Before
    public void registerDrone() {
        droneRepository.save(Drone.builder()
                .serialNumber(SERIAL_NUMBER)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(100L))
                .state(State.IDLE)
                .weightLimit(500.00)
                .build());
    }

    @After
    public void clearDatabase() {
        droneRepository.deleteAll();
    }


    @Test
    public void getDroneBatteryLevel_success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                        get("http://localhost:9090/api/v1/drone/get-drone-battery-level?serialNumber="+SERIAL_NUMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteryCapacity").exists())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void getDroneBatteryLevel_failOnInvalidSerialNumber() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                        get("http://localhost:9090/api/v1/drone/get-drone-battery-level?serialNumber="+RandomStringUtils.randomAlphabetic(10))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }
}
