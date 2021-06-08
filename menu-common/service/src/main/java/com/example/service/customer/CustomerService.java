package com.example.service.customer;

import com.example.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);

    Customer getById(Long customerId);

    void delete(Long customerId);

    Customer getByEmail(String email);

    List<Customer> getAll();

    List<Customer> getAllVoted();

    Customer update(Customer customer, Long customerId);
}
