package com.example.controller;


import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.service.menu.MenuService;
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

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping(value = "/rate")
    public ResponseEntity<MenuRatedDto> vote(@RequestBody VoteDto voteDto,
                                        @PathVariable("restId") Long restaurantId) {

        log.info("Voting for menu of a restaurant with ID = {}", restaurantId);
        Double rate = voteDto.getRate();
        String menuId = voteDto.getMenuId();

        Double averageRate = rateService.calculateRate(menuId, rate, restaurantId);
        MenuRatedDto menuRatedDto = rateService.updateRate(menuId, restaurantId, averageRate);

        return new ResponseEntity<>(menuRatedDto, HttpStatus.OK);
    }
}
