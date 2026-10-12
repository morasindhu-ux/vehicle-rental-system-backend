package com.example.vehicle_rental.dto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class RentalRequest {

    @NotNull(message = "Customer ID is required")
private Long customerId;

@NotNull(message = "Vehicle ID is required")
private Long vehicleId;

@NotNull(message = "Start date is required")
private LocalDate startDate;

@NotNull(message = "End date is required")
private LocalDate endDate;

    public RentalRequest() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}