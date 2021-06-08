package com.example.service.customer;

import com.example.CustomerRepository;
import com.example.customer.Customer;
import com.example.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        notNull(customer, "The customer must be not NULL");
        log.info("Storing a customer");

        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long customerId) {
        notNull(customerId, "The customer ID must be not NULL");

        Optional<Customer> possibleCustomer = customerRepository.findById(customerId);

        if(possibleCustomer.isPresent()) {
            log.info("Receiving a customer with ID = {}", customerId);
            return possibleCustomer.get();
        }

        log.info("The exception for a customer with ID = {} has been occurred", customerId);
        throw new CustomerNotFoundException(String
                                                .format("The customer with ID = %d not found", customerId));
    }

    @Override
    public void delete(Long customerId) {
        notNull(customerId, "The customer ID must be not NULL");
        log.info("Removing a customer with ID = {}", customerId);

        customerRepository.deleteById(customerId);
    }

    @Override
    public Customer getByEmail(String email) {
        notNull(email, "The customer email must be not NULL");
        log.info("Receiving a customer with email = {}" ,email);

        Optional<Customer> possibleCustomer = customerRepository.getCustomerByEmail(email);

        if(possibleCustomer.isPresent()) {
            log.info("Receiving a customer with email = {}", email);
            return possibleCustomer.get();
        }

        log.info("The exception for a customer with email = {} has been occurred", email);
        throw new CustomerNotFoundException(String
                .format("The customer with email = %d not found", email));
    }

    @Override
    public List<Customer> getAll() {
        log.info("Receiving a list of all customers");

        return (List) customerRepository.findAll();
    }

    @Override
    public List<Customer> getAllVoted() {
        log.info("Receiving a list of all already voted customers");

        return customerRepository.getAllByVotedTrue();
    }
}
