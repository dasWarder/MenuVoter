package com.example.service.rate;

import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.exception.EntityNotFoundException;
import com.example.menu.Menu;

public interface RateService {

    Double calculateRateForMenu(Menu menu, Double userRate);

    MenuRatedDto updateMenuRateForRestaurant(VoteDto voteDto, Long restaurantId) throws EntityNotFoundException;
}
