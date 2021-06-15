package com.example.service.vote;

import com.example.customer.Customer;
import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.service.customer.CustomerService;
import com.example.service.mail.MailService;
import com.example.service.rate.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.springframework.util.Assert.notNull;

@Slf4j
@Service
public class VoteServiceImpl implements VoteService {

    private final RateService rateService;

    private final CustomerService customerService;

    private final MailService mailService;

    @Autowired
    public VoteServiceImpl(RateService rateService, CustomerService customerService, MailService mailService) {
        this.rateService = rateService;
        this.customerService = customerService;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public MenuRatedDto vote(VoteDto voteDto, Long restaurantId) {
        notNull(voteDto, "The vote DTO must be not NULL");
        notNull(restaurantId, "The restaurant ID must be not NULL");

        String email = voteDto.getEmail();
        Customer customer = customerService.getCustomerByEmail(email);

        if(customer == null) {
            customer = customerService.saveCustomer(new Customer(email));
        }

        if(!customer.isVoted()) {
            customerService.updateCustomer(
                    new Customer(customer.getEmail(), true), customer.getId());

            log.info("Voting for menu of a restaurant with ID = {}", restaurantId);
            MenuRatedDto menuRatedDto = rateService.updateRate(voteDto, restaurantId);

            log.info("Send a thankful message to a customer with email = {}", customer.getEmail());
            mailService.sendMail(customer);

            return menuRatedDto;
        }

        return null;
    }
}
