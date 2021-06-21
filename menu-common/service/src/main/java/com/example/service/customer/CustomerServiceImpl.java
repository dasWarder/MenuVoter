package com.example.service.customer;

import com.example.CustomerRepository;
import com.example.customer.Customer;
import com.example.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.util.OptionalValidation.checkOptionalAndReturnOrThrowException;
import static com.example.util.ParamValidationUtil.validateParametersNotNull;

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

        validateParametersNotNull(customerForSave);
        log.info("Storing a customer with email = {}",
                                                      customerForSave.getEmail());
        customerForSave.setVoted(false);

        return customerRepository.save(customerForSave);
    }


    @Override
    public Customer getCustomerById(Long customerId) throws EntityNotFoundException {

        validateParametersNotNull(customerId);
        log.info("Receiving a customer with ID = {}",
                                                     customerId);
        Optional<Customer> possibleCustomerById = customerRepository.getCustomerById(customerId);
        Customer customerFromDB = checkOptionalAndReturnOrThrowException(possibleCustomerById, Customer.class);

        return customerFromDB;
    }


    @Override
    @Transactional
    public void deleteCustomerById(Long customerId) {

        validateParametersNotNull(customerId);
        log.info("Removing a customer with ID = {}",
                                                    customerId);
        customerRepository.deleteCustomerById(customerId);
    }


    @Override
    public Customer getCustomerByEmail(String email) throws EntityNotFoundException {

        validateParametersNotNull(email);
        log.info("Receiving a customer with email = {}" ,
                                                         email);
        Optional<Customer> possibleCustomerByEmail = customerRepository.getCustomerByEmail(email);
        Customer customerFromDB = checkOptionalAndReturnOrThrowException(possibleCustomerByEmail, Customer.class);

        return customerFromDB;
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
    public Customer updateCustomer(Customer updatingCustomer, Long customerId) throws EntityNotFoundException {

        validateParametersNotNull(updatingCustomer, customerId);
        log.info("Update the customer with ID = {}",
                                                   customerId);

        validateCustomerForUpdate(customerId);
        updatingCustomer.setId(customerId);
        Customer updatedCustomer = customerRepository.save(updatingCustomer);

        return updatedCustomer;
    }





    private void validateCustomerForUpdate(Long customerId) throws EntityNotFoundException {
        Optional<Customer> possibleCustomerById = customerRepository.getCustomerById(customerId);
        Customer customer = checkOptionalAndReturnOrThrowException(possibleCustomerById, Customer.class);
    }
}
