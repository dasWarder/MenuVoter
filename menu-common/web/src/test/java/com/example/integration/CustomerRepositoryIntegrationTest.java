package com.example.integration;


import com.example.CustomerRepository;
import com.example.customer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.example.util.TestCustomerData.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = { "classpath:db/customer/init_customer.sql", "classpath:db/customer/populate_customer.sql"})
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldStoreCustomerProperly() {

        log.info("Test correctness of storing a correct customer to the database");
        Customer customerForStoring = SAVE_CUSTOMER;

        Customer storedCustomer = customerRepository.save(customerForStoring);
        customerForStoring.setId(storedCustomer.getId());

        assertThat(storedCustomer)
                                  .usingRecursiveComparison()
                                  .isEqualTo(customerForStoring);
    }

    @Test
    public void shouldReturnListOfAllCustomersProperly() {

        log.info("Test correctness of receiving a list of all customers");
        List<Customer> expectedCustomersList = List.of(TEST_CUSTOMER_1, TEST_CUSTOMER_2, TEST_CUSTOMER_3);

        List<Customer> customersFromDb = (List) customerRepository.findAll();

        assertThat(customersFromDb)
                                  .usingRecursiveComparison()
                                  .isEqualTo(expectedCustomersList);
    }

    @Test
    public void shouldReceiveCustomerByIdProperly() {

        log.info("Test correctness of receiving a customer by its ID");
        Long customerId = TEST_CUSTOMER_1.getId();

        Optional<Customer> possibleCustomerById = customerRepository.getCustomerById(customerId);
        Customer customerFromDb = possibleCustomerById.get();

        assertThat(customerFromDb)
                                .usingRecursiveComparison()
                                .isEqualTo(TEST_CUSTOMER_1);
    }

    @Test
    public void shouldReceiveCustomerByEmailProperly() {

        log.info("Test correctness of receiving a customer by its email");
        String email = TEST_CUSTOMER_2.getEmail();

        Optional<Customer> possibleCustomerByEmail = customerRepository.getCustomerByEmail(email);
        Customer customerFromDb = possibleCustomerByEmail.get();

        assertThat(customerFromDb)
                                .usingRecursiveComparison()
                                .isEqualTo(TEST_CUSTOMER_2);
    }

    @Test
    public void shouldDeleteCustomerByIdProperly() {

        log.info("Test correctness of removing a customer by its ID");
        Long customerId = TEST_CUSTOMER_1.getId();

        customerRepository.deleteCustomerById(customerId);

        assertThat(
                    customerRepository.getCustomerById(customerId))
                    .isEqualTo(Optional.empty());
    }

    @Test
    public void shouldUpdateCustomerProperly() {

        log.info("Test correctness of updating a customer properly");
        Long customerIdToUpdate = TEST_CUSTOMER_2.getId();

        Customer customerToUpdate = UPDATE_CUSTOMER;
        customerToUpdate.setId(customerIdToUpdate);

        assertThat(
                customerRepository
                        .getCustomerById(customerIdToUpdate)
                        .get())
                .usingRecursiveComparison()
                .isEqualTo(TEST_CUSTOMER_2);

        Customer storedUpdatedCustomer = customerRepository.save(customerToUpdate);


        assertThat(customerIdToUpdate)
                .isEqualTo(storedUpdatedCustomer.getId());

        assertThat(storedUpdatedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customerToUpdate);
    }

    @Test
    public void shouldReturnListOfVotedCustomersProperly() {

        log.info("Test correctness of receiving a list of voted customers");
        List<Customer> expectedVotedCustomers = List.of(TEST_CUSTOMER_3);

        List<Customer> customersByVotedTrue = customerRepository.getCustomersByVotedTrue();

        assertThat(customersByVotedTrue)
                .usingRecursiveComparison()
                .isEqualTo(expectedVotedCustomers);
    }
}
