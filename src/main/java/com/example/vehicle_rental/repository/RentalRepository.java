package com.example.vehicle_rental.repository;

import com.example.vehicle_rental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}