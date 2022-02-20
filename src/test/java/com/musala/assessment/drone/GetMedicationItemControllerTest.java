package com.musala.assessment.drone;

import com.musala.assessment.drone.enums.Model;
import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.model.Drone;
import com.musala.assessment.drone.model.Medication;
import com.musala.assessment.drone.repository.DroneRepository;
import com.musala.assessment.drone.repository.MedicationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetMedicationItemControllerTest extends BaseIntegrationTest{

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    private static final String SERIAL_NUMBER = RandomStringUtils.randomAlphabetic(10);

    @Before
    public void registerDrone() {
        Drone drone = droneRepository.save(Drone.builder()
                .serialNumber(SERIAL_NUMBER)
                .model(Model.LightWeight)
                .batteryCapacity(BigDecimal.valueOf(100L))
                .state(State.IDLE)
                .weightLimit(500.00)
                .build());

        medicationRepository.save(Medication.builder()
                .drone(drone)
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .image(RandomStringUtils.randomAlphanumeric(20))
                .name(RandomStringUtils.randomAlphabetic(10))
                .weight(200.00)
                .build());

        medicationRepository.save(Medication.builder()
                .drone(drone)
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .image(RandomStringUtils.randomAlphanumeric(20))
                .name(RandomStringUtils.randomAlphabetic(10))
                .weight(200.00)
                .build());

        medicationRepository.save(Medication.builder()
                .drone(drone)
                .code(RandomStringUtils.randomAlphabetic(10).toUpperCase())
                .image(RandomStringUtils.randomAlphanumeric(20))
                .name(RandomStringUtils.randomAlphabetic(10))
                .weight(100.00)
                .build());
    }

    @Test
    public void getAllMedicationItemTest_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        get("http://localhost:9090/api/v1/drone/get-all-medication-items?serialNumber="+SERIAL_NUMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
