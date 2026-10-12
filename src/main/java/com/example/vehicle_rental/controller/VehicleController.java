package com.example.vehicle_rental.controller;

import com.example.vehicle_rental.entity.Vehicle;
import com.example.vehicle_rental.service.VehicleService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // GET all vehicles
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    // GET vehicle by ID
    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    // POST - add vehicle
   @PostMapping
public Vehicle addVehicle(@Valid @RequestBody Vehicle vehicle) {
    return vehicleService.addVehicle(vehicle);
}

    // PUT - update vehicle
    @PutMapping("/{id}")
    public Vehicle updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle vehicle) {

        return vehicleService.updateVehicle(id, vehicle);
    }

    // DELETE vehicle
    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable Long id) {

        vehicleService.deleteVehicle(id);

        return "Vehicle deleted successfully";
    }
}