package com.example.vehicle_rental.service;

import com.example.vehicle_rental.entity.Customer;
import com.example.vehicle_rental.exception.ResourceNotFoundException;
import com.example.vehicle_rental.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Customer getCustomerById(Long id) {
       return customerRepository.findById(id)
        .orElseThrow(() ->
                new ResourceNotFoundException(
                        "Customer not found with id: " + id
                )
        );
    }

    // Add customer
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Update customer
    public Customer updateCustomer(Long id, Customer customer) {

        Customer existingCustomer =
                customerRepository.findById(id).orElse(null);

        if (existingCustomer == null) {
            return null;
        }

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setLicenseNumber(customer.getLicenseNumber());

        return customerRepository.save(existingCustomer);
    }

    // Delete customer
   public void deleteCustomer(Long id) {

    if (!customerRepository.existsById(id)) {
        throw new ResourceNotFoundException(
                "Customer not found with id: " + id
        );
    }

    customerRepository.deleteById(id);
}
}