package com.example.service.rate;

import com.example.dto.MenuRatedDto;

public interface RateService {

    Double calculateRate(String menuId, Double userRate, Long restaurantId);

    MenuRatedDto updateRate(String menuId, Long restaurantId, Double averageMenuRate);
}
