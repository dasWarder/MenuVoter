package com.example.util;

import com.example.customer.Customer;

public class TestCustomerData {

    public static final Customer TEST_CUSTOMER_1 = new Customer(100_000L, "alex@gmail.com", false);
    public static final Customer TEST_CUSTOMER_2 = new Customer(100_001L, "james@gmail.com", false);
    public static final Customer SAVE_CUSTOMER = new Customer(null, "stored@gmail.com", true);
}
