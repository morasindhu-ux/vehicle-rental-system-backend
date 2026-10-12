package com.example.vehicle_rental.service;

import com.example.vehicle_rental.entity.Vehicle;
import com.example.vehicle_rental.exception.ResourceNotFoundException;
import com.example.vehicle_rental.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get vehicle by ID
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
        .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Vehicle not found with id: " + id
                )
        );
    }

    // Add vehicle
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Update vehicle
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {

        Vehicle existingVehicle =
                vehicleRepository.findById(id).orElse(null);

        if (existingVehicle == null) {
            return null;
        }

        existingVehicle.setVehicleNumber(vehicle.getVehicleNumber());
        existingVehicle.setBrand(vehicle.getBrand());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setType(vehicle.getType());
        existingVehicle.setPricePerDay(vehicle.getPricePerDay());
        existingVehicle.setAvailable(vehicle.isAvailable());

        return vehicleRepository.save(existingVehicle);
    }

    // Delete vehicle
    public void deleteVehicle(Long id) {
       
     if (!vehicleRepository.existsById(id)) {
        throw new ResourceNotFoundException(
                "Vehicle not found with id: " + id
        );
    }

    vehicleRepository.deleteById(id);
}
}