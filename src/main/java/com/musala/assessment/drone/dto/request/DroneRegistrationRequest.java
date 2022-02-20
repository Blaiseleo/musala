package com.musala.assessment.drone.dto.request;

import com.musala.assessment.drone.enums.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneRegistrationRequest {

    @NotBlank(message = "serial number required")
    @Size(max = 100, message = "max characters size is 100")
    @Pattern(regexp = "[a-zA-Z]+", message = "only characters allowed (A-Z, a-z) for field serialNumber")
    private String serialNumber;

    @NotNull(message = "model required")
    private Model model;

    @NotNull(message = "weightLimit required")
    @Min(value = 0, message="weight limit must be within 0 - 500gr")
    @Max(value = 500, message="weight limit must be within 0 - 500gr")
    private double weightLimit;
}
