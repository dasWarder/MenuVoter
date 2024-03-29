package com.example.scheduler;

import com.example.customer.Customer;
import com.example.exception.EntityNotFoundException;
import com.example.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CustomerScheduling {

    private final CustomerService customerService;

    @Autowired
    public CustomerScheduling(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Scheduled(cron = "0 0 1 * * *")
    public void makeAvailableAlreadyVotedCustomersVotingNewDay() {

        log.info("Updating already voted yesterday customers");
        List<Customer> alreadyVotedCustomers = customerService.getAllVotedCustomers();

        alreadyVotedCustomers.forEach(votedCustomer -> {

            Customer updatedCustomer = new Customer(votedCustomer.getEmail(), false);
            updateVotedCustomers(updatedCustomer, votedCustomer.getId());

        });
    }

    @Scheduled(cron = "0 0 1 * * SUN")
    public void removeCustomersInTheEndOfWeek() {

        log.info("Removing customers on Sunday");
        List<Customer> customers = customerService.getAllCustomers();

        customers.forEach(customer -> {

            customerService.deleteCustomerById(customer.getId());
        });
    }





    private void updateVotedCustomers(Customer updatedCustomer, Long customerId) {

        try {

            customerService.updateCustomer(updatedCustomer,
                                                          customerId);

        } catch (EntityNotFoundException e) {

            e.printStackTrace();

        }
    }
}
