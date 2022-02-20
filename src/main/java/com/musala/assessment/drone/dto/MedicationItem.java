package com.musala.assessment.drone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationItem {

    private String name;

    private double weight;

    private String code;

    private String image;
}
