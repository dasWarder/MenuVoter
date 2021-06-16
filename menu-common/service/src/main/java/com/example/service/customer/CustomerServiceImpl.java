package com.example.service.customer;

import com.example.CustomerRepository;
import com.example.customer.Customer;
import com.example.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public Customer saveCustomer(Customer customerForSave) {
        notNull(customerForSave, "The customer must be not NULL");
        log.info("Storing a customer with email = {}", customerForSave.getEmail());
        customerForSave.setVoted(false);

        return customerRepository.save(customerForSave);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        notNull(customerId, "The customer ID must be not NULL");

        Optional<Customer> possibleCustomerById = customerRepository.getCustomerById(customerId);

        if(possibleCustomerById.isPresent()) {
            log.info("Receiving a customer with ID = {}", customerId);
            return possibleCustomerById.get();
        }

        log.info("The exception for a customer with ID = {} has been occurred", customerId);
        throw new CustomerNotFoundException(String
                                                .format("The customer with ID = %d not found", customerId));
    }

    @Override
    @Transactional
    public void deleteCustomerById(Long customerId) {
        notNull(customerId, "The customer ID must be not NULL");
        log.info("Removing a customer with ID = {}", customerId);

        customerRepository.deleteCustomerById(customerId);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        notNull(email, "The customer email must be not NULL");
        log.info("Receiving a customer with email = {}" ,email);

        Optional<Customer> possibleCustomerByEmail = customerRepository.getCustomerByEmail(email);

        if(possibleCustomerByEmail.isPresent()) {
            log.info("Receiving a customer with email = {}", email);
            return possibleCustomerByEmail.get();
        }

        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("Receiving a list of all customers");

        return (List) customerRepository.findAll();
    }

    @Override
    public List<Customer> getAllVotedCustomers() {
        log.info("Receiving a list of all already voted customers");

        return customerRepository.getCustomersByVotedTrue();
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer updatingCustomer, Long customerId) {
        notNull(updatingCustomer, "The customer must be not NULL");
        notNull(customerId, "The customer ID must be not NULL");

        log.info("Update the customer with ID = {}", customerId);
        updatingCustomer.setId(customerId);
        Customer updatedCustomer = customerRepository.save(updatingCustomer);

        return updatedCustomer;
    }
}
