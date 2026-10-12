package com.example.vehicle_rental.repository;

import com.example.vehicle_rental.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}