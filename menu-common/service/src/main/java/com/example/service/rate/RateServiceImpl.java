package com.example.service.rate;


import com.example.MenuRepository;
import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.exception.EntityNotFoundException;
import com.example.menu.Menu;
import com.example.service.mapping.MappingService;
import com.example.service.rate.util.VoteCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import static com.example.util.OptionalValidation.checkOptionalAndReturnOrThrowException;
import static com.example.util.ParamValidationUtil.validateParametersNotNull;

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

        validateParametersNotNull(menu, userRate);

        voteCounter.incrementCurrentCountOfVotesForMenu(menu);
        Double averageRate = getAverageRateForMenu(menu, userRate);

        return averageRate;
    }


    @Override
    @Transactional
    public MenuRatedDto updateMenuRateForRestaurant(VoteDto voteDto, Long restaurantId) throws EntityNotFoundException {

        validateParametersNotNull(voteDto, restaurantId);

        Menu storedMenu = calculateNewRateForMenuAndUpdateOrThrowException(voteDto, restaurantId);
        MenuRatedDto menuRatedDto = mappingService.mappingFromMenuToRatedDto(storedMenu);

        return menuRatedDto;
    }






    private Double getAverageRateForMenu(Menu menu, Double userRate) {

        String menuId = menu.getId();
        //in case when there is no rate for the menu need to add a new 0.0 value
        computeIfMenuRateIsAbsent(menu, menuId);
        log.info("Calculating average menu rate for menu with ID = {}",
                                                                      menu.getId());
        rateMap.computeIfPresent(menuId,
                                 getCountingFunction(menu, userRate));

        return rateMap.get(menuId);
    }


    private void computeIfMenuRateIsAbsent(Menu menu, String menuId) {

        rateMap.computeIfAbsent(menuId, k -> {

            log.info("Creating a new rate for a menu with ID = {}",
                                                                  menuId);
            return menu.getRate();
        });
    }


    private BiFunction<String, Double, Double> getCountingFunction(Menu menu, Double userRate) {

        //for the first vote the formula must be different, when the counter < 1 (only one vote)
        Long votesCount = menu.getVotesCount();
        BiFunction<String, Double, Double> countingFunction = calculateCountingFunction(votesCount, userRate);

        return countingFunction;
    }


    private BiFunction<String, Double, Double> calculateCountingFunction(Long votesCount, Double userRate) {

        BiFunction<String, Double, Double> countingFunction;

        if(votesCount < 1) {
            //i.e. if userRate = 6.0 for the first vote the formula will be calculate like 6.0 + 0.0
            countingFunction = (menuId,rate) ->
                                               (rate + userRate);

        } else {
            //i.e. userRate = 7.0 and the average rate for the menu is 5.5
            countingFunction = (menuId,rate) ->
                                               (rate + userRate) / 2;
        }

        return countingFunction;
    }


    private Menu calculateNewRateForMenuAndUpdateOrThrowException(VoteDto voteDto, Long restaurantId)
                                                                                   throws EntityNotFoundException {
        Double userRate = voteDto.getRate();

        Menu currentMenuFromDB = getMenuOrException(voteDto, restaurantId);
        Double updatedAverageMenuRate = calculateRateForMenu(currentMenuFromDB, userRate);
        Menu storedMenuWithUpdatedRate = updateMenuFromDB(currentMenuFromDB, updatedAverageMenuRate);

        return storedMenuWithUpdatedRate;
    }


    private Menu getMenuOrException(VoteDto voteDto, Long restaurantId)
                                                     throws EntityNotFoundException {
        String menuId = voteDto.getMenuId();
        log.info("Get menu with ID = {} to update it for voting",
                                                                 menuId);
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);
        Menu menu = checkOptionalAndReturnOrThrowException(possibleMenu, Menu.class);

        return menu;
    }


    private Menu updateMenuFromDB(Menu menuFromDB, Double averageMenuRate) {

        menuFromDB.setRate(averageMenuRate);
        menuFromDB.setVotesCount(voteCounter
                                            .getCurrentCountOfVotesForMenu(menuFromDB));
        Menu storedMenu = menuRepository.save(menuFromDB);

        return storedMenu;
    }

}
