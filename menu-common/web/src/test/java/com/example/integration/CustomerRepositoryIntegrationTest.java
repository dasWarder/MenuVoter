package com.example.integration;


import com.example.CustomerRepository;
import com.example.customer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.util.TestCustomerData.SAVE_CUSTOMER;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Sql(scripts = { "classpath:db/customer/init_customer.sql", "classpath:db/customer/populate_customer.sql"})
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldStoreCustomerProperly() {

        log.info("Test storing a correct customer to the database");
        Customer customerForStoring = SAVE_CUSTOMER;

        Customer storedCustomer = customerRepository.save(customerForStoring);
        customerForStoring.setId(storedCustomer.getId());

        Assertions.assertThat(storedCustomer)
                                            .usingRecursiveComparison()
                                            .isEqualTo(customerForStoring);
    }
}
