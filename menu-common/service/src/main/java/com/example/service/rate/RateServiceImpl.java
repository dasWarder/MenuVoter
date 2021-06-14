package com.example.service.rate;


import com.example.MenuRepository;
import com.example.customer.Customer;
import com.example.dto.MenuRatedDto;
import com.example.dto.VoteDto;
import com.example.menu.Menu;
import com.example.service.customer.CustomerService;
import com.example.service.mail.MailService;
import com.example.service.mapping.MappingService;
import com.example.service.rate.util.VoteCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import static com.example.service.rate.util.NonNullableMenu.getNonNullableMenu;
import static org.springframework.util.Assert.notNull;

@Slf4j
@Service
public class RateServiceImpl implements RateService {

    private final VoteCounter voteCounter;

    private final MenuRepository menuRepository;

    private final MappingService mappingService;

    private final CustomerService customerService;

    private final MailService mailService;

    private final ConcurrentHashMap<String, Double> rateMap = new ConcurrentHashMap<>();

    @Autowired
    public RateServiceImpl(VoteCounter voteCounter, MenuRepository menuRepository,
                           MappingService mappingService, CustomerService customerService,
                           MailService mailService) {
        this.voteCounter = voteCounter;
        this.menuRepository = menuRepository;
        this.mappingService = mappingService;
        this.customerService = customerService;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public Double calculateRate(Menu menu, Double userRate) {
        notNull(menu, "The menu must be not NULL");
        notNull(userRate, "The user rate must be not NULL");

        voteCounter.incrementCounter(menu);
        Double averageRate = getAverageRate(menu, userRate);

        return averageRate;
    }

    @Override
    @Transactional
    public MenuRatedDto updateRate(VoteDto voteDto, Long restaurantId) {
        notNull(voteDto, "The vote DTO must be not NULL");
        notNull(voteDto, "The restaurant ID must be not NULL");

        String menuId = voteDto.getMenuId();
        Double userRate = voteDto.getRate();

        Menu storedMenu = getAndUpdateMenu(menuId, restaurantId, userRate);

        log.info("Mapping a menu with ID = {} to RatedDTO object for upgrading the current menu");
        MenuRatedDto menuRatedDto = mappingService.fromMenuToRatedDto(storedMenu);

        return menuRatedDto;
    }

    @Override
    @Transactional
    public MenuRatedDto vote(VoteDto voteDto, Long restaurantId) {
        notNull(voteDto, "The vote DTO must be not NULL");
        notNull(restaurantId, "The restaurant ID must be not NULL");

        String email = voteDto.getEmail();
        Customer customer = customerService.getByEmail(email);

        if(customer == null) {
            customer = customerService.save(new Customer(email));
        }

        if(!customer.isVoted()) {
            customerService.update(
                    new Customer(customer.getEmail(), true), customer.getId());

            log.info("Voting for menu of a restaurant with ID = {}", restaurantId);
            MenuRatedDto menuRatedDto = updateRate(voteDto, restaurantId);

            log.info("Send a thankful message to a customer with email = {}", customer.getEmail());
            mailService.sendMail(customer);

            return menuRatedDto;
        }

        return null;
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

    private Menu getAndUpdateMenu(String menuId, long restaurantId, Double userRate) {
        log.info("Get menu with ID = {} to update it for voting", menuId);

        Menu menu = getNonNullableMenu(menuRepository, menuId, restaurantId);
        Double averageMenuRate = calculateRate(menu, userRate);

        menu.setRate(averageMenuRate);
        menu.setVotes(voteCounter.getCurrentCount(menu));
        Menu storedMenu = menuRepository.save(menu);

        return storedMenu;
    }
}
