package com.example.controller;


import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.service.vote.VoteService;
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
@RequestMapping( "/restaurants/restaurant/{restId}/menus/menu")
public class RateController {

    private static final String VOTED_MESSAGE = "You've already voted today, please back tomorrow!";

    private final VoteService voteService;

    @Autowired
    public RateController(VoteService voteService) {
        this.voteService = voteService;
    }


    @PutMapping( "/rate")
    public ResponseEntity voteRestaurant(@RequestBody
                                         @Valid
                                         VoteDto voteDto,
                                         @PathVariable("restId")
                                         @Min(value = 1, message =
                                                 "The Id must be greater that 0")
                                         @NotNull(message =
                                                 "The Id of a restaurant must be not null")
                                         Long restaurantId) {

        MenuRatedDto vote = voteService.voteForMenu(voteDto, restaurantId);

        return new ResponseEntity(vote == null?
                                                VOTED_MESSAGE : vote,
                                                                    HttpStatus.OK);
    }

}
