package com.example.service.rate;

import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.menu.Menu;

public interface RateService {

    Double calculateRate(Menu menu, Double userRate);

    MenuRatedDto updateRate(VoteDto voteDto, Long restaurantId);

    MenuRatedDto vote(VoteDto voteDto, Long restaurantId);
}
