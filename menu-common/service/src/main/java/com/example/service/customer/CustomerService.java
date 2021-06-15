package com.example.service.customer;

import com.example.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customerForSave);

    Customer getCustomerById(Long customerId);

    void deleteCustomerById(Long customerId);

    Customer getCustomerByEmail(String email);

    List<Customer> getAllCustomers();

    List<Customer> getAllVotedCustomers();

    Customer updateCustomer(Customer updatingCustomer, Long customerId);
}
