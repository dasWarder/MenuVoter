package com.example.service.rate;


import com.example.Menu;
import com.example.MenuRepository;
import com.example.dto.MenuRatedDto;
import com.example.exception.MenuNotFoundException;
import com.example.service.mapping.MappingService;
import com.example.service.rate.util.VoteCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import static org.springframework.util.Assert.notNull;

@Slf4j
@Service
public class RateServiceImpl implements RateService {

    private final VoteCounter voteCounter;

    private final MenuRepository menuRepository;

    private final MappingService mappingService;

    private final ConcurrentHashMap<String, Double> rateMap = new ConcurrentHashMap<>();

    @Autowired
    public RateServiceImpl(VoteCounter voteCounter, MenuRepository menuRepository, MappingService mappingService) {
        this.voteCounter = voteCounter;
        this.menuRepository = menuRepository;
        this.mappingService = mappingService;
    }

    @Override
    public Double calculateRate(String menuId, Double userRate, Long restaurantId) {
        notNull(menuId, "The menu ID must be not NULL");

        voteCounter.incrementCounter(menuId);
        Double averageRate = getAverageRate(menuId, userRate);

        return averageRate;
    }

    @Override
    public MenuRatedDto updateRate(String menuId, Long restaurantId, Double averageMenuRate) {
        notNull(menuId, "The ID of a menu must be not NULL");
        notNull(restaurantId, "The ID of a restaurant must be not NULL");
        notNull(averageMenuRate, "The average rate must be not NULL");

        Menu menu = getNonNullableMenu(menuId, restaurantId);
        menu.setRate(averageMenuRate);
        Menu storedMenu = menuRepository.save(menu);

        log.info("Mapping a menu with ID = {} to RatedDTO object for upgrading the current menu");
        MenuRatedDto menuRatedDto = mappingService.fromMenuToRatedDto(storedMenu);

        return menuRatedDto;
    }

    private Menu getNonNullableMenu(String menuId, Long restaurantId) {
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);

        if (possibleMenu.isPresent()) {
            log.info("Receiving a menu with ID = {} for the restaurant with ID = {}", menuId, restaurantId);
            Menu menu = possibleMenu.get();

            return menu;
        }

        throw new MenuNotFoundException(String
                                        .format("The menu with ID = %s for a restaurant with ID = %d hasn't been found",
                                                                                                    menuId, restaurantId));
    }


    private Double getAverageRate(String menuId, Double userRate) {
        rateMap.computeIfAbsent(menuId, k -> {
            log.info("Creating a new rate for a menu with ID = {}", menuId);
            return 0.0d;
        });

        log.info("Calculate average menu rate for menu with ID = {}", menuId);
        rateMap.computeIfPresent(menuId, getCountingFunction(menuId, userRate));

        return rateMap.get(menuId);
    }

    private BiFunction<String, Double, Double> getCountingFunction(String menuId, Double userRate) {
        BiFunction<String, Double, Double> countingFunction;

        if(voteCounter.getCurrentCount(menuId) == 1 && rateMap.get(menuId) == 0.0d ) {
            log.info("Creating counting function for the first rate for menu with ID = {}", menuId);
            countingFunction = (k,v) -> (v + userRate);
        } else {
            log.info("Creating counting function for the first rate for menu with ID = {}", menuId);
            countingFunction = (k,v) -> (v + userRate) / 2;
        }

        return countingFunction;
    }
}
