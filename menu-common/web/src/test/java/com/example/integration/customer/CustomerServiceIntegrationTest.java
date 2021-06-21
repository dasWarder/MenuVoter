package com.example.integration.customer;

import com.example.customer.Customer;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.EntityNotFoundException;
import com.example.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static com.example.util.TestCustomerData.*;
import static com.example.util.TestDishData.WRONG_ID;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = { "classpath:db/customer/init_customer.sql", "classpath:db/customer/populate_customer.sql"})
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void shouldReceiveAllCustomersProperly() {

        log.info("Test correctness of receiving a list of all customers");
        List<Customer> expectedAllCustomers = List.of(
                                                      TEST_CUSTOMER_1,
                                                      TEST_CUSTOMER_2,
                                                      TEST_CUSTOMER_3);

        List<Customer> listOfAllCustomers = customerService.getAllCustomers();

        assertThat(listOfAllCustomers)
                                .usingRecursiveComparison()
                                .isEqualTo(expectedAllCustomers);
    }

    @Test
    public void shouldSaveCustomerProperly() {

        log.info("Test correctness of storing a customer to the database");
        Customer customerForStore = SAVE_CUSTOMER;

        Customer storedCustomer = customerService.saveCustomer(SAVE_CUSTOMER);
        customerForStore.setId(
                                storedCustomer.getId());

        assertThat(storedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customerForStore);
    }

    @Test
    public void shouldThrowExceptionWhenTryStoreNullCustomer() {

        log.info("Test throwing exception when try to store a null customer");
        Customer customerToStore = null;

        assertThrows(NullPointerException.class,
                    () -> customerService.saveCustomer(customerToStore));
    }

    @Test
    public void shouldReceiveListOfAllVotedProperly() {

        log.info("Test correctness of receiving a list of all voted customers");
        List<Customer> expectedAlreadyVotedCustomers = List.of(TEST_CUSTOMER_3);

        List<Customer> allVotedCustomers = customerService.getAllVotedCustomers();

        assertThat(allVotedCustomers)
                            .usingRecursiveComparison()
                            .isEqualTo(expectedAlreadyVotedCustomers);
    }


    @Test
    public void shouldReceiveCustomerByIdProperly() throws EntityNotFoundException {

        log.info("Test correctness of receiving a customer by its ID");
        Long customerId = TEST_CUSTOMER_1.getId();

        Customer customerById = customerService.getCustomerById(customerId);

        assertThat(customerById)
                            .usingRecursiveComparison()
                            .isEqualTo(TEST_CUSTOMER_1);
    }

    @Test
    public void shouldThrowExceptionWhenTryToGetByNullId() {

        log.info("Test throwing NullPointerException when try to get a customer by NULL ID");
        Long customerId = null;

        assertThrows(NullPointerException.class,
                        () -> customerService.getCustomerById(customerId));
    }

    @Test
    public void shouldThrowExceptionWhenTryToGetWrongCustomer() {

        log.info("Test throwing CustomerNotFoundException when try to get a wrong customer");
        Long customerId = WRONG_ID;

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById(customerId));
    }


    @Test
    public void shouldReceiveCustomerByEmailProperly() throws EntityNotFoundException {

        log.info("Test correctness of receiving a customer by its email");
        String email = TEST_CUSTOMER_3.getEmail();

        Customer customerFromDb = customerService.getCustomerByEmail(email);

        assertThat(customerFromDb)
                            .usingRecursiveComparison()
                            .isEqualTo(TEST_CUSTOMER_3);
    }


    @Test
    public void shouldThrowExceptionWhenTryToReceiveByNullEmail() {

        log.info("Test throwing NullPointerException when try to get a customer by NULL email");
        String email = null;

        assertThrows(NullPointerException.class,
                        () -> customerService.getCustomerByEmail(email));
    }

    @Test
    public void shouldThrowExceptionWhenTryToReceiveByWrongEmail() {

        log.info("Test throwing CustomerNotFoundException when try to get a customer by wrong email");
        String email = WRONG_EMAIL;

        assertThrows(CustomerNotFoundException.class,
                        () -> customerService.getCustomerByEmail(email));
    }


    @Test
    public void shouldRemoveCustomerByIdProperly() throws EntityNotFoundException {

        log.info("Test removing a customer by its ID");
        Long customerId = TEST_CUSTOMER_2.getId();
        Customer customerFromDb = customerService.getCustomerById(customerId);

        assertThat(customerFromDb)
                .usingRecursiveComparison()
                .isEqualTo(TEST_CUSTOMER_2);

        customerService.deleteCustomerById(customerId);

        assertThrows(CustomerNotFoundException.class,
                        () -> customerService.getCustomerById(customerId));
    }
    

    @Test
    public void shouldThrowExceptionWhenTryToRemoveNullableId() {

        log.info("Test throwing NullPointerException when try to remove a customer with NULL ID");
        Long customerId = null;

        assertThrows(NullPointerException.class,
                        () -> customerService.deleteCustomerById(customerId));
    }


    @Test
    public void shouldUpdateCustomerProperly() throws EntityNotFoundException {

        log.info("Test correctness of updating a customer");
        Long customerIdToUpdate = TEST_CUSTOMER_3.getId();
        Customer customerForUpdate = UPDATE_CUSTOMER;

        Customer customerFromDb = customerService.getCustomerById(customerIdToUpdate);

        assertThat(customerFromDb)
                .usingRecursiveComparison()
                .isEqualTo(TEST_CUSTOMER_3);

        Customer updatedCustomer = customerService.updateCustomer(customerForUpdate, customerIdToUpdate);
        customerForUpdate.setId(customerIdToUpdate);

        assertThat(updatedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customerForUpdate);
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateWithNullableId() {

        log.info("Test throwing NullPointerException when try to update a customer with NULL ID");
        Long customerId = null;
        Customer customerToUpdate = UPDATE_CUSTOMER;

        assertThrows(NullPointerException.class,
                        () -> customerService.updateCustomer(customerToUpdate, null));
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateCustomerWithNullableUpdatingCustomer() {

        log.info("Test throwing NullPointerException when try to update a customer with NULL updatingCustomer param");
        Long customerId = TEST_CUSTOMER_3.getId();
        Customer customerToUpdate = null;

        assertThrows(NullPointerException.class,
                        () -> customerService.updateCustomer(customerToUpdate, customerId));
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateCustomerWithWrongId() {

        log.info("Test throwing CustomerNotFoundException when try to update a wrong customer");
        Long customerId = WRONG_ID;
        Customer customerToUpdate = UPDATE_CUSTOMER;

        assertThrows(CustomerNotFoundException.class,
                        () -> customerService.updateCustomer(customerToUpdate, customerId));
    }

}
