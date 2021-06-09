package com.example.controller;


import com.example.customer.Customer;
import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.service.customer.CustomerService;
import com.example.service.rate.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "/restaurants/restaurant/{restId}/menus/menu")
public class RateController {

    private static final String VOTED_MESSAGE = "You already voted today, please back tomorrow!";

    private final RateService rateService;

    private final CustomerService customerService;

    @Autowired
    public RateController(RateService rateService, CustomerService customerService) {
        this.rateService = rateService;
        this.customerService = customerService;
    }

    @PutMapping(value = "/rate")
    public ResponseEntity vote(@RequestBody VoteDto voteDto,
                                        @PathVariable("restId") Long restaurantId) {

        String email = voteDto.getEmail();
        Customer customer = customerService.getByEmail(email);

        if(customer == null) {
            customer = customerService.save(new Customer(email));
        }

        if(!customer.isVoted()) {
            customerService.update(
                    new Customer(customer.getEmail(), true),
                    customer.getId());

            log.info("Voting for menu of a restaurant with ID = {}", restaurantId);
            MenuRatedDto menuRatedDto = rateService.updateRate(voteDto, restaurantId);

            return new ResponseEntity(menuRatedDto, HttpStatus.OK);
        }


        return new ResponseEntity(VOTED_MESSAGE, HttpStatus.OK);
    }
}
