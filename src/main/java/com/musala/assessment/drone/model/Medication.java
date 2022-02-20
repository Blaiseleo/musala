package com.musala.assessment.drone.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "medication")
public class Medication extends BaseEntity {

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "drone_id", nullable = false)
    private Drone drone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String image;
}
