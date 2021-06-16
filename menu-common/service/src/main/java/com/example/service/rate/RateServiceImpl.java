package com.example.service.rate;


import com.example.MenuRepository;
import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.menu.Menu;
import com.example.service.mapping.MappingService;
import com.example.service.rate.util.VoteCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import static com.example.service.rate.util.NonNullableMenu.checkMenuNotNullAndGet;
import static org.springframework.util.Assert.notNull;

@Slf4j
@Service
public class RateServiceImpl implements RateService {

    private final VoteCounter voteCounter;

    private final MenuRepository menuRepository;

    private final MappingService mappingService;

    private final ConcurrentHashMap<String, Double> rateMap = new ConcurrentHashMap<>();

    @Autowired
    public RateServiceImpl(VoteCounter voteCounter, MenuRepository menuRepository,
                                                    MappingService mappingService) {
        this.voteCounter = voteCounter;
        this.menuRepository = menuRepository;
        this.mappingService = mappingService;
    }

    @Override
    @Transactional
    public Double calculateRateForMenu(Menu menu, Double userRate) {
        notNull(menu,
                "The menu must be not NULL");
        notNull(userRate,
                "The user rate must be not NULL");
        voteCounter.incrementVoteCounterForMenu(menu);
        Double averageRate = getAverageRateForMenu(menu, userRate);

        return averageRate;
    }

    @Override
    @Transactional
    public MenuRatedDto updateMenuRateForRestaurant(VoteDto voteDto, Long restaurantId) {

        notNull(voteDto,
                "The vote DTO must be not NULL");
        notNull(voteDto,
                "The restaurant ID must be not NULL");
        String menuId = voteDto
                               .getMenuId();
        Double userRate = voteDto
                                 .getRate();
        Menu storedMenu = getAndUpdateMenu(menuId, restaurantId, userRate);
        MenuRatedDto menuRatedDto = mappingService.mappingFromMenuToRatedDto(storedMenu);

        return menuRatedDto;
    }

    private Double getAverageRateForMenu(Menu menu, Double userRate) {

        String menuId = menu.getId();

        //in case when there is no rate for the menu need to add a new 0.0 value
        rateMap.computeIfAbsent(menuId, k -> {

                log.info("Creating a new rate for a menu with ID = {}",
                                                                       menuId);
                return menu.getRate();
        });

        log.info("Calculating average menu rate for menu with ID = {}",
                                                                     menuId);
        rateMap.computeIfPresent(menuId, getCountingFunction(menu, userRate));

        return rateMap.get(menuId);
    }

    private BiFunction<String, Double, Double> getCountingFunction(Menu menu, Double userRate) {

        BiFunction<String, Double, Double> countingFunction;

        //for the first vote the formula must be different, when the counter < 1 (only one vote)
        if(menu.getVotesCount() < 1) {

                //i.e. if userRate = 6.0 for the first vote the formula will be calculate like 6.0 + 0.0
                log.info("Creating counting function for the first rate for menu with ID = {} and rate = {}",
                                                                                                            menu.getId(),
                                                                                                            menu.getRate());
                countingFunction = (menuId,rate) ->
                                            (rate + userRate);

        } else {

                //i.e. userRate = 7.0 and the average rate for the menu is 5.5 then
                // 5.5 + 7.0 = 12.5 and 12.5/2 = 6.25
                log.info("Creating counting function for the first rate for menu with ID = {} and rate = {}",
                                                                                                            menu.getId(),
                                                                                                            menu.getRate());
                countingFunction = (menuId,rate) ->
                                            (rate + userRate) / 2;
        }

        return countingFunction;
    }

    private Menu getAndUpdateMenu(String menuId, long restaurantId, Double userRate) {

        log.info("Get menu with ID = {} to update it for voting",
                                                                 menuId);
        Menu menu = checkMenuNotNullAndGet(menuRepository, menuId, restaurantId);
        Double averageMenuRate = calculateRateForMenu(menu, userRate);

        menu.setRate(
                     averageMenuRate);
        menu.setVotesCount(
                           voteCounter.getCurrentCountOfVotesForMenu(menu));
        Menu storedMenu = menuRepository.save(menu);

        return storedMenu;
    }
}
