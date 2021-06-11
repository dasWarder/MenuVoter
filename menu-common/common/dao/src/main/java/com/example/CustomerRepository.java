package com.example;

import com.example.customer.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> getCustomerByEmail(String email);

    List<Customer> getAllByVotedTrue();
}
