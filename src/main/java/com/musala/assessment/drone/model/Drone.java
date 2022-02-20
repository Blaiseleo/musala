package com.musala.assessment.drone.model;

import com.musala.assessment.drone.enums.Model;
import com.musala.assessment.drone.enums.State;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "drone")
public class Drone extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(nullable = false)
    private double weightLimit;

    private double currentWeight;

    @Column(nullable = false, precision = 5 , scale = 2)
    private BigDecimal batteryCapacity;

    @Enumerated(EnumType.STRING)
    private State state;
}
