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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Slf4j
@RestController
@RequestMapping(value = "/restaurants/restaurant/{restId}/menus/menu")
public class RateController {

    private static final String VOTED_MESSAGE = "You already voted today, please back tomorrow!";

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PutMapping(value = "/rate")
    public ResponseEntity vote(@RequestBody @Valid VoteDto voteDto,
                                        @PathVariable("restId")
                                            @Min(value = 1, message = "The Id must be greater that 0")
                                            @NotNull(message = "The Id of a restaurant must be not null") Long restaurantId) {

        MenuRatedDto vote = rateService.vote(voteDto, restaurantId);

        return vote == null?
                new ResponseEntity(VOTED_MESSAGE, HttpStatus.OK) :
                                new ResponseEntity(vote, HttpStatus.OK);
    }


}
