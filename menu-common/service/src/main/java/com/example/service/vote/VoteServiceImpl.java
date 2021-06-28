package com.example.service.vote;

import com.example.customer.Customer;
import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.exception.CustomerNotFoundException;
import com.example.exception.EntityNotFoundException;
import com.example.service.customer.CustomerService;
import com.example.service.mail.MailService;
import com.example.service.rate.RateService;
import com.example.util.ParamValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class VoteServiceImpl implements VoteService {

    private final RateService rateService;

    private final CustomerService customerService;

    private final MailService mailService;

    @Autowired
    public VoteServiceImpl(RateService rateService, CustomerService customerService,
                                                    MailService mailService) {
        this.rateService = rateService;
        this.customerService = customerService;
        this.mailService = mailService;
    }


    @Override
    @Transactional
    public MenuRatedDto voteForMenu(VoteDto voteDto, Long restaurantId) throws EntityNotFoundException {

        ParamValidationUtil.validateParametersNotNull(voteDto, restaurantId);

        Customer customer = getOrCreateCustomer(voteDto);
        MenuRatedDto updatedMenuWithRate = checkCustomerIsVotedAndUpdateItOrReturnNull(customer, voteDto, restaurantId);

        if (updatedMenuWithRate != null) {

            sendThankfulEmailToCustomer(customer);

        }

        return updatedMenuWithRate;
    }






    private Customer getOrCreateCustomer(VoteDto voteDto) {

        String email = voteDto.getEmail();
        Customer customer = null;

        try {

            customer = customerService.getCustomerByEmail(email);

        } catch (CustomerNotFoundException exception) {

            customer = customerService.saveCustomer(new Customer(email));

        } finally {

            return customer;
        }
    }


    private MenuRatedDto checkCustomerIsVotedAndUpdateItOrReturnNull(Customer customerToCheckVote, VoteDto voteDto,
                                                                                                   Long restaurantId)
                                                                                                   throws EntityNotFoundException {
        if (!customerToCheckVote.isVoted()) {

            MenuRatedDto menuRatedDtoForMenuWithUpdatedRate = updateCustomerAndRate(customerToCheckVote, voteDto, restaurantId);

            return menuRatedDtoForMenuWithUpdatedRate;
        }

        return null;
    }


    private MenuRatedDto updateCustomerAndRate(Customer customer, VoteDto voteDto, Long restaurantId) throws EntityNotFoundException {

        customerService.updateCustomer(new Customer(customer.getEmail(), true),
                                                                                    customer.getId());
        log.info("Voting for menu of a restaurant with ID = {}",
                                                                restaurantId);
        MenuRatedDto menuRatedDtoForMenuWithUpdatedRate = rateService.updateMenuRateForRestaurant(voteDto, restaurantId);

        return menuRatedDtoForMenuWithUpdatedRate;
    }


    private void sendThankfulEmailToCustomer(Customer customerToCheckVote) {

        log.info("Send a thankful message to a customer with email = {}",
                                                                        customerToCheckVote.getEmail());
        mailService.sendMail(customerToCheckVote);
    }
}
