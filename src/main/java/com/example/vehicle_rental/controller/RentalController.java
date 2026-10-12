package com.example.vehicle_rental.controller;

import com.example.vehicle_rental.dto.RentalRequest;
import com.example.vehicle_rental.entity.Rental;
import com.example.vehicle_rental.service.RentalService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@CrossOrigin(origins = "*")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }
 
    @PostMapping
    public Rental createRental(@Valid @RequestBody RentalRequest request) {
        return rentalService.createRental(request);
}
    // Get all rentals
    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    // Get rental by ID
    @GetMapping("/{id}")
    public Rental getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id);
    }


    // Return vehicle
    @PutMapping("/{id}/return")
    public Rental returnVehicle(@PathVariable Long id) {

        return rentalService.returnVehicle(id);
    }
}