package com.example.menu;

import com.example.MenuRepository;
import com.example.Menu;
import com.example.exception.MenuNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.notNull;


@Slf4j
@Service
@EnableMongoRepositories(basePackageClasses = { MenuRepository.class })
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> getAllByRestaurantId(long restaurantId) {
        log.info("Receiving a collection of menus for the restaurant with ID = ", restaurantId);
        return menuRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public Menu save(Menu menu, long restaurantId) {
        log.info("Storing the menu with ID = {} for a restaurant with ID = {}", menu.getId(), restaurantId);
        menu.setRestaurantId(restaurantId);

        return menuRepository.save(menu);
    }

    @Override
    public Menu getById(String menuId, long restaurantId) {
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);

        if(possibleMenu.isPresent()) {
            log.info("Receiving the menu by its ID = ", menuId);
            return possibleMenu.get();
        }

        log.info("The exception for menu with ID = {} has been occurred", menuId);
        throw new MenuNotFoundException(String
                .format("The menu with ID = %s not founded", menuId));
    }

    @Override
    public void deleteById(String menuId, long restaurantId) {
        log.info("Removing the menu with ID = ", menuId);
        menuRepository.deleteMenuByIdAndRestaurantId(menuId, restaurantId);
    }

    @Override
    public Menu getByCreatingDate(LocalDateTime creatingDate) {
        Optional<Menu> possibleMenu = menuRepository.getMenuByCreatingDate(creatingDate);

        if(possibleMenu.isPresent()) {
            log.info("Receiving the menu with the date of creating = ", creatingDate.toString());
            return possibleMenu.get();
        }

        log.info("The exception for creating date = {} has been occurred", creatingDate.toString());
        throw new MenuNotFoundException(String
                .format("The menu with creating date = %s not founded", creatingDate));
    }

    @Override
    public Menu update(long restaurantId, String menuId, Menu menu) {
        Optional<Menu> possibleMenu = menuRepository.getMenuByIdAndRestaurantId(menuId, restaurantId);

        if(possibleMenu.isPresent()) {
            log.info("Updating the menu with ID = {} and a restaurant ID = {}", menuId, restaurantId);

            String menuFromDbId = possibleMenu.get().getId();
            menu.setId(menuFromDbId);
            menu.setRestaurantId(restaurantId);

            return menuRepository.save(menu);
        }

        log.info("The exception for updating the menu with ID = {} has been occurred", menuId);
        throw new MenuNotFoundException(String
                .format("The menu with ID = %s and the restaurant ID =  %d not founded", menuId, restaurantId));
    }
}
