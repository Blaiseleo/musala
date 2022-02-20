package com.musala.assessment.drone.repository;

import com.musala.assessment.drone.enums.State;
import com.musala.assessment.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    Optional<Drone> findBySerialNumber(String serialNumber);

    List<Drone> findAllByStateIn(List<State> state);
}
