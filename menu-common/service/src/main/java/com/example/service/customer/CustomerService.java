package com.example.service.customer;

import com.example.customer.Customer;
import com.example.exception.EntityNotFoundException;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customerForSave);

    Customer getCustomerById(Long customerId) throws EntityNotFoundException;

    void deleteCustomerById(Long customerId);

    Customer getCustomerByEmail(String email) throws EntityNotFoundException;

    List<Customer> getAllCustomers();

    List<Customer> getAllVotedCustomers();

    Customer updateCustomer(Customer updatingCustomer, Long customerId) throws EntityNotFoundException;
}
