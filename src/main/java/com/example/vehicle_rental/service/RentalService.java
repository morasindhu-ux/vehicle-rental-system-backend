package com.example.vehicle_rental.service;

import com.example.vehicle_rental.dto.RentalRequest;
import com.example.vehicle_rental.entity.Customer;
import com.example.vehicle_rental.entity.Rental;
import com.example.vehicle_rental.entity.Vehicle;
import com.example.vehicle_rental.repository.CustomerRepository;
import com.example.vehicle_rental.repository.RentalRepository;
import com.example.vehicle_rental.repository.VehicleRepository;

import com.example.vehicle_rental.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public RentalService(
            RentalRepository rentalRepository,
            CustomerRepository customerRepository,
            VehicleRepository vehicleRepository) {

        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    // Get all rentals
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Get rental by ID
    public Rental getRentalById(Long id) {
    return rentalRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Rental not found with id: " + id));
}

    // Create rental
    @Transactional
    public Rental createRental(RentalRequest request) {

        Customer customer =
                customerRepository.findById(request.getCustomerId())
                        .orElseThrow(() ->
        new ResourceNotFoundException("Customer not found"));

        Vehicle vehicle =
                vehicleRepository.findById(request.getVehicleId())
                        .orElseThrow(() ->
        new ResourceNotFoundException("Vehicle not found"));

        // Check vehicle availability
        if (!vehicle.isAvailable()) {
            throw new RuntimeException("Vehicle is not available");
        }

        // Check dates
        if (request.getStartDate() == null ||
                request.getEndDate() == null) {

            throw new RuntimeException("Start date and end date are required");
        }

        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new RuntimeException(
                    "End date must be after start date");
        }

        // Calculate number of days
        long numberOfDays =
                ChronoUnit.DAYS.between(
                        request.getStartDate(),
                        request.getEndDate());

        // Calculate total amount
        double totalAmount =
                numberOfDays * vehicle.getPricePerDay();

        // Create rental
        Rental rental = new Rental();

        rental.setCustomer(customer);
        rental.setVehicle(vehicle);
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setNumberOfDays(numberOfDays);
        rental.setTotalAmount(totalAmount);
        rental.setStatus("ACTIVE");

        // Save rental
        Rental savedRental = rentalRepository.save(rental);

        // Make vehicle unavailable
        vehicle.setAvailable(false);
        vehicleRepository.save(vehicle);

        return savedRental;
    }

    // Return vehicle
    @Transactional
    public Rental returnVehicle(Long rentalId) {

        Rental rental =
        rentalRepository.findById(rentalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Rental not found with id: " + rentalId));

       if (!rental.getStatus().equals("ACTIVE")) {
    throw new IllegalStateException(
            "Rental is already completed");
}

        // Change rental status
        rental.setStatus("COMPLETED");

        // Make vehicle available again
        Vehicle vehicle = rental.getVehicle();
        vehicle.setAvailable(true);

        vehicleRepository.save(vehicle);

        return rentalRepository.save(rental);
    }
}