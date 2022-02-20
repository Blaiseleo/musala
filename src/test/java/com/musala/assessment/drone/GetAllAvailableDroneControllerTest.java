package com.musala.assessment.drone;

import com.musala.assessment.drone.enums.Model;
import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.repository.DroneRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllAvailableDroneControllerTest extends BaseIntegrationTest{

    @Autowired
    private DroneRepository droneRepository;

    private static final String SERIAL_NUMBER_IDLE = RandomStringUtils.randomAlphabetic(10);
    private static final String SERIAL_NUMBER_LOADED = RandomStringUtils.randomAlphabetic(10);
    private static final String SERIAL_NUMBER_LOADING = RandomStringUtils.randomAlphabetic(10);

    @Before
    public void registerDrone() {
        Drone drone = Drone.builder()
                .serialNumber(SERIAL_NUMBER_IDLE)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(100L))
                .state(State.IDLE)
                .weightLimit(500.00)
                .build();

        Drone droneInWrongState = Drone.builder()
                .serialNumber(SERIAL_NUMBER_LOADED)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(100L))
                .state(State.LOADED)
                .weightLimit(500.00)
                .build();

        Drone droneWithBatteryLevelBelow25 = Drone.builder()
                .serialNumber(SERIAL_NUMBER_LOADING)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(100L))
                .state(State.LOADING)
                .weightLimit(500.00)
                .build();

        List<Drone> droneList = new ArrayList<>();
        droneList.add(drone);
        droneList.add(droneInWrongState);
        droneList.add(droneWithBatteryLevelBelow25);

        droneRepository.saveAll(droneList);
    }

    @Test
    public void getAllAvailableDronesTest_success() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.
                        get("http://localhost:9090/api/v1/drone/get-all-available-drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains(State.IDLE.name()) || content.contains(State.LOADING.name()));
    }
}
