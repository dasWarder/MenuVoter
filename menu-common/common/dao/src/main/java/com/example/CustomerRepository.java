package com.example;

import com.example.customer.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer saveCustomer(Customer customer);

    List<Customer> getCustomers();

    void deleteCustomerById(Long customerId);

    Optional<Customer> getCustomerById(Long customerId);

    Optional<Customer> getCustomerByEmail(String email);

    List<Customer> getCustomersByVotedTrue();
}
