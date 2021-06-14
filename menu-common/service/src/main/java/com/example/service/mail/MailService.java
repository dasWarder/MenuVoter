package com.example.service.mail;

import com.example.customer.Customer;

public interface MailService {

    String THANKFUL_TEMPLATE = "thankful";

    void sendMail(Customer customer);
}
