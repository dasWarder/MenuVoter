package com.example.service.customer;

import com.example.CustomerRepository;
import com.example.customer.Customer;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Optional;

import static com.example.service.TestData.*;


@Slf4j
class CustomerServiceTest {

    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);

    private final CustomerService customerService = new CustomerServiceImpl(customerRepository);

    @Test
    public void shouldSaveCustomerProperly() {
        log.info("Test correctness of storing a customer");
        Mockito.when(customerRepository.save(TEST_CUSTOMER_NO_ID)).thenReturn(TEST_CUSTOMER_WITH_ID);

        Customer storedCustomer = customerService.saveCustomer(TEST_CUSTOMER_NO_ID);

        Assertions.assertEquals(TEST_CUSTOMER_WITH_ID, storedCustomer);
    }

    @Test
    public void shouldThrowExceptionWhenCustomerIsNull() {
        log.info("Test throwing IllegalArgumentException when try to store NULL customer");
        Customer customer = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> customerService.saveCustomer(customer));
    }

    @Test
    public void shouldReturnCustomerByIdProperly() throws EntityNotFoundException {
        log.info("Test correctness of receiving a customer by ID");
        Mockito.when(customerRepository.getCustomerById(TEST_CUSTOMER_2.getId())).thenReturn(Optional.of(TEST_CUSTOMER_2));

        Customer customer = customerService.getCustomerById(TEST_CUSTOMER_2.getId());

        Assertions.assertEquals(TEST_CUSTOMER_2, customer);
    }

    @Test
    public void shouldThrowExceptionWhenNullId() {
        log.info("Test throwing IllegalArgumentException when try to get a customer by ID");
        Long customerId = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> customerService.getCustomerById(customerId));
    }

    @Test
    public void shouldThrowExceptionWhenWrongId() {
        log.info("Test throwing CustomerNotFoundException when a customer ID is wrong");
        Mockito.when(customerRepository.findById(WRONG_ID)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById(WRONG_ID));
    }

    @Test
    public void shouldThrowExceptionWhenIdNull() {
        log.info("Test throwing IllegalArgumentException when delete a customer with NULL ID");
        Long customerId = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> customerService.deleteCustomerById(customerId));
    }

    @Test
    public void shouldReturnCustomerByEmailProperly() throws EntityNotFoundException {
        log.info("Test correctness of receiving a customer by email");
        String email = TEST_CUSTOMER_WITH_ID.getEmail();
        Mockito.when(customerRepository.getCustomerByEmail(email)).thenReturn(Optional.of(TEST_CUSTOMER_WITH_ID));

        Customer customerByEmail = customerService.getCustomerByEmail(email);

        Assertions.assertEquals(TEST_CUSTOMER_WITH_ID, customerByEmail);
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsNull() {
        log.info("Test throwing IllegalArgumentException when try to get a customer with NULL email");
        String email = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> customerService.getCustomerByEmail(email));
    }

    @Test
    public void shouldThrowExceptionWhenWrongEmail() {
        log.info("Test throwing CustomerNotFoundException");
        String email = WRONG_EMAIL;

        Assertions.assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerByEmail(email));
    }

    @Test
    public void shouldReturnAllCustomersProperly() {
        log.info("Test correctness of receiving a list of all customers");
        Mockito.when(customerRepository.findAll()).thenReturn(ALL_CUSTOMERS);

        List<Customer> customers = customerService.getAllCustomers();

        Assertions.assertEquals(ALL_CUSTOMERS, customers);
    }

    @Test
    public void shouldReturnAllVotedCustomersProperly() {
        log.info("Test correctness of receiving a list of voted customers");
        Mockito.when(customerRepository.getCustomersByVotedTrue()).thenReturn(VOTED_CUSTOMERS);

        List<Customer> votedCustomers = customerService.getAllVotedCustomers();

        Assertions.assertEquals(VOTED_CUSTOMERS, votedCustomers);
    }

    @Test
    public void shouldReturnUpdatedCustomerProperly() {
        log.info("Test correctness of updating a customer");
        Mockito.when(customerRepository.save(UPDATED_CUSTOMER)).thenReturn(UPDATED_CUSTOMER);
        Long customerId = UPDATED_CUSTOMER.getId();
        Customer updatingCustomer = TEST_UPDATING_CUSTOMER;
        updatingCustomer.setId(customerId);

        customerService.updateCustomer(updatingCustomer, customerId);

        Assertions.assertEquals(UPDATED_CUSTOMER, updatingCustomer);
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingCustomerIsNull() {
        log.info("Test throwing IllegalArgumentException when try to update a NULL customer");
        Customer customer = null;
        Long customerId = UPDATED_CUSTOMER.getId();

        Assertions.assertThrows(NullPointerException.class,
                () -> customerService.updateCustomer(customer, customerId));
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingCustomerIdIsNull() {
        log.info("Test throwing IllegalArgumentException when try to update a customer with NULL customer ID param");
        Customer customer = TEST_UPDATING_CUSTOMER;
        Long customerId = null;

        Assertions.assertThrows(NullPointerException.class,
                () -> customerService.updateCustomer(customer, customerId));
    }

}