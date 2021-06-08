package com.example.service.rate;


import com.example.menu.Menu;
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
        Menu menu = getNonNullableMenu(menuId, restaurantId);

        voteCounter.incrementCounter(menu);
        Double averageRate = getAverageRate(menu, userRate);

        return averageRate;
    }

    @Override
    public MenuRatedDto updateRate(String menuId, Long restaurantId, Double averageMenuRate) {
        notNull(menuId, "The ID of a menu must be not NULL");
        notNull(restaurantId, "The ID of a restaurant must be not NULL");
        notNull(averageMenuRate, "The average rate must be not NULL");

        Menu menu = getNonNullableMenu(menuId, restaurantId);
        menu.setRate(averageMenuRate);
        menu.setVotes(voteCounter.getCurrentCount(menu));
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


    private Double getAverageRate(Menu menu, Double userRate) {
        String menuId = menu.getId();
        //in case when there is no rate for the menu need to add a new 0.0 value
        rateMap.computeIfAbsent(menuId, k -> {
            log.info("Creating a new rate for a menu with ID = {}", menuId);
            return menu.getRate();
        });

        log.info("Calculate average menu rate for menu with ID = {}", menuId);
        rateMap.computeIfPresent(menuId, getCountingFunction(menu, userRate));

        return rateMap.get(menuId);
    }

    private BiFunction<String, Double, Double> getCountingFunction(Menu menu, Double userRate) {
        BiFunction<String, Double, Double> countingFunction;
        //for the first vote the formula must be different, when the counter < 1 (only one vote)
        if(menu.getVotes() < 1) {
            //i.e. if userRate = 6.0 for the first vote the formula will be calculate like 6.0 + 0.0
            log.info("Creating counting function for the first rate for menu with ID = {} and rate = {}", menu.getId());
            countingFunction = (k,v) -> (v + userRate);
        } else {
            //i.e. userRate = 7.0 and the average rate for the menu is 5.5 then
            // 5.5 + 7.0 = 12.5 and 12.5/2 = 6.25
            log.info("Creating counting function for the first rate for menu with ID = {} and rate = {}", menu.getId());
            countingFunction = (k,v) -> (v + userRate) / 2;
        }

        return countingFunction;
    }
}
