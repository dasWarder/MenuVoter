package com.example.service.rate.util;

import com.example.MenuRepository;
import com.example.exception.MenuNotFoundException;
import com.example.menu.Menu;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class NonNullableMenu {

    public static Menu getNonNullableMenu(MenuRepository menuRepository, String menuId, Long restaurantId) {
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
}
