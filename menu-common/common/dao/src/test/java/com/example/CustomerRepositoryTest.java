package com.example;

import com.example.customer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.example.TestData.TEST_CUSTOMER_3;
import static com.example.TestData.VOTED_CUSTOMERS;

@Slf4j
//@SpringBootTest(classes = { DaoConfig.class })
class CustomerRepositoryTest {

    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);

    @BeforeEach
    public void init() {
        Mockito.when(customerRepository.getCustomerByEmail("david@gmail.com"))
                .thenReturn(Optional.of(TEST_CUSTOMER_3));

        Mockito.when(customerRepository.getCustomersByVotedTrue()).thenReturn(VOTED_CUSTOMERS);
    }

    @Test
    public void shouldReturnCustomerByEmailProperly() {
        log.info("Test correctness of receiving a customer by ID");
        Optional<Customer> possibleCustomer = customerRepository.getCustomerByEmail("david@gmail.com");

        Customer customer = possibleCustomer.get();

        Assertions.assertEquals(TEST_CUSTOMER_3, customer);
    }

    @Test
    public void shouldReturnAllVotedCustomersProperly() {
        log.info("Test correctness of receiving a list of already voted customers");
        List<Customer> votedCustomers = customerRepository.getCustomersByVotedTrue();

        Assertions.assertEquals(VOTED_CUSTOMERS, votedCustomers);
    }

}