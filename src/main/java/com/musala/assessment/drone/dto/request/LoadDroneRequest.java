package com.musala.assessment.drone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadDroneRequest {

    /**
     * - name (allowed only letters, numbers, ‘-‘, ‘_’);
     * - weight;
     * - code (allowed only upper case letters, underscore and numbers);
     * - image (picture of the medication case).
     */

    @NotBlank(message = "drone serial number required")
    private String droneSerialNumber;

    @NotBlank(message = "name required")
    @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "only letters, numbers, ‘-‘, ‘_’ allowed for field name")
    private String name;

    @NotNull(message = "weight required")
    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]*$", message = "only upper case letters, underscore and numbers allowed for field code")
    private String code;

    @NotBlank(message = "image required")
    private String image;
}
