package com.example.vehicle_rental.repository;

import com.example.vehicle_rental.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}