package com.musala.assessment.drone;

import com.musala.assessment.drone.dto.request.DroneRegistrationRequest;
import com.musala.assessment.drone.enums.Model;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterDroneControllerTest extends BaseIntegrationTest{

    @Test
    public void registerDroneTest_success() throws Exception {

        DroneRegistrationRequest registrationRequest = DroneRegistrationRequest.builder()
                .model(Model.LightWeight)
                .serialNumber(RandomStringUtils.randomAlphabetic(10))
                .weightLimit(500.00)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/register")
                        .content(asJsonString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void registerDroneTest_failOnSerialNumberAlphaNumeric() throws Exception {

        DroneRegistrationRequest registrationRequest = DroneRegistrationRequest.builder()
                .model(Model.LightWeight)
                .serialNumber(RandomStringUtils.randomAlphanumeric(10))
                .weightLimit(500.00)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/register")
                        .content(asJsonString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void registerDroneTest_failOnSerialNumberGreaterThan100() throws Exception {

        DroneRegistrationRequest registrationRequest = DroneRegistrationRequest.builder()
                .model(Model.LightWeight)
                .serialNumber(RandomStringUtils.randomAlphabetic(101))
                .weightLimit(500.00)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/register")
                        .content(asJsonString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void registerDroneTest_failOnWeightLimitGreaterThan500() throws Exception {

        DroneRegistrationRequest registrationRequest = DroneRegistrationRequest.builder()
                .model(Model.LightWeight)
                .serialNumber(RandomStringUtils.randomAlphabetic(101))
                .weightLimit(600.00)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.
                        post("http://localhost:9090/api/v1/drone/register")
                        .content(asJsonString(registrationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }
}
