package com.musala.assessment.drone.dto.response;

import com.musala.assessment.drone.dto.MedicationItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneMedicationItemsResponse {
    private String serialNumber;
    private List<MedicationItem> medicationItems;
}
