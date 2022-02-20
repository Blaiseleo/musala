package com.musala.assessment.drone;

import com.musala.assessment.drone.dto.request.LoadDroneRequest;
import com.musala.assessment.drone.enums.Model;
import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.repository.DroneRepository;
import com.musala.assessment.drone.repository.MedicationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoadDroneControllerTest extends BaseIntegrationTest{

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    private static final String SERIAL_NUMBER = RandomStringUtils.randomAlphabetic(10);
    private static final String SERIAL_NUMBER_BATTERY_LEVEL = RandomStringUtils.randomAlphabetic(10);
    private static final String SERIAL_NUMBER_WRONG_STATE = RandomStringUtils.randomAlphabetic(10);

    @Before
    public void registerDrone() {
        Drone drone = Drone.builder()
                .serialNumber(SERIAL_NUMBER)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(100L))
                .state(State.IDLE)
                .weightLimit(500.00)
                .build();

        Drone droneInWrongState = Drone.builder()
                .serialNumber(SERIAL_NUMBER_WRONG_STATE)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(100L))
                .state(State.LOADED)
                .weightLimit(500.00)
                .build();

        Drone droneWithBatteryLevelBelow25 = Drone.builder()
                .serialNumber(SERIAL_NUMBER_BATTERY_LEVEL)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(24L))
                .state(State.IDLE)
                .weightLimit(500.00)
                .build();

        List<Drone> droneList = new ArrayList<>();
        droneList.add(drone);
        droneList.add(droneInWrongState);
        droneList.add(droneWithBatteryLevelBelow25);

        droneRepository.saveAll(droneList);
    }

    @After
    public void clearDatabase() {
        medicationRepository.deleteAll();
        droneRepository.deleteAll();
    }

    @Test
    public void loadDroneTest_success() throws Exception {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber(SERIAL_NUMBER)
                .name(RandomStringUtils.randomAlphabetic(10))
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .weight(200.00)
                .image(RandomStringUtils.randomAlphanumeric(20))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/load-drone")
                        .content(asJsonString(loadDroneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void loadDroneTest_failOnWrongSerialNumber() throws Exception {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber(RandomStringUtils.randomAlphabetic(10))
                .name(RandomStringUtils.randomAlphabetic(10))
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .weight(200.00)
                .image(RandomStringUtils.randomAlphanumeric(20))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/load-drone")
                        .content(asJsonString(loadDroneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void loadDroneTest_failOnInvalidName() throws Exception {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber(SERIAL_NUMBER)
                .name(RandomStringUtils.randomAlphabetic(10)+"@")
                .weight(200.00)
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .image(RandomStringUtils.randomAlphanumeric(20))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/load-drone")
                        .content(asJsonString(loadDroneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void loadDroneTest_failOnInvalidCode() throws Exception {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber(SERIAL_NUMBER)
                .name(RandomStringUtils.randomAlphabetic(10))
                .code(RandomStringUtils.randomAlphabetic(10))
                .weight(200.00)
                .image(RandomStringUtils.randomAlphanumeric(20))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/load-drone")
                        .content(asJsonString(loadDroneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void loadDroneTest_failOnOverWeight() throws Exception {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber(SERIAL_NUMBER)
                .name(RandomStringUtils.randomAlphabetic(10))
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .weight(600.00)
                .image(RandomStringUtils.randomAlphanumeric(20))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/load-drone")
                        .content(asJsonString(loadDroneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void loadDroneTest_failOnBatteryLevelBelow25() throws Exception {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber(SERIAL_NUMBER_BATTERY_LEVEL)
                .name(RandomStringUtils.randomAlphabetic(10))
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .weight(500.00)
                .image(RandomStringUtils.randomAlphanumeric(20))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/load-drone")
                        .content(asJsonString(loadDroneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void loadDroneTest_failOnDroneInWrongState() throws Exception {

        LoadDroneRequest loadDroneRequest = LoadDroneRequest.builder()
                .droneSerialNumber(SERIAL_NUMBER_WRONG_STATE)
                .name(RandomStringUtils.randomAlphabetic(10))
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .weight(500.00)
                .image(RandomStringUtils.randomAlphanumeric(20))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/load-drone")
                        .content(asJsonString(loadDroneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }
}
