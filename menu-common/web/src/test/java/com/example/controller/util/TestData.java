package com.example.controller.util;

import com.example.dto.MenuDto;
import com.example.menu.Dish;
import com.example.menu.Menu;
import com.example.restaurant.Restaurant;

import java.time.LocalDate;
import java.util.List;

public class TestData {
    public static final String NAME_PARAM = "name";
    public static final Restaurant FIRST_RESTAURANT = new Restaurant(1L, "Monaco", "The lux restaurant of Italian cousins");
    public static final Restaurant SECOND_RESTAURANT = new Restaurant(2L, "Wasabi", "The restaurant of Japanese and European cousins");
    public static final Restaurant[] MAIN_PAGE_RESTAURANTS = new Restaurant[]{ FIRST_RESTAURANT, SECOND_RESTAURANT };
    public static final Restaurant CREATE_RESTAURANT = new Restaurant(1L, "Buffalo", "The best steak house");
    public static final Restaurant UPDATE_RESTAURANT = new Restaurant(null, "Updated", "Updated");
    public static final Restaurant UPDATED_RESTAURANT = new Restaurant(CREATE_RESTAURANT.getId(), "Updated", "Updated");
    public static final String DELETE_MESSAGE = "The restaurant with ID = " + UPDATED_RESTAURANT.getId() + " was successfully removed";

    public static final Dish TEST_DISH = new Dish("Risotto", "Classic Italian risotto");
    public static final Dish TEST_DISH_2 = new Dish("Pizza margarete", "Classic Italian pizza");
    public static final Dish TEST_DISH_3 = new Dish("Pasta with seafood", "The best past in this town");
    public static final Dish TEST_DISH_4 = new Dish("Cappuccino", "A cup of cold cappuccino with ice");
    public static final Dish TEST_DISH_5 = new Dish("Miso soup", "The classic japanese miso soup");
    public static final Dish TEST_DISH_6 = new Dish("Yakitori", "The traditional japanese cousine");
    public static final Dish TEST_DISH_7 = new Dish("Matcha", "A coup of matcha tea");
    public static final Dish TEST_DISH_9 = new Dish("Omuraisu", "A typical omuraisu");

    public static final Menu FIRST_RESTAURANT_FIRST_MENU = new Menu("60c1a31acb80bdb5e24c44cc", 1L, 0.0, 0L, LocalDate.of(2021, 06, 9),
            List.of(TEST_DISH));
    public static final Menu FIRST_RESTAURANT_SECOND_MENU = new Menu("60c1a31acb80bdb5e24c44cd", 1L, 0.0, 0L, LocalDate.now(),
            List.of(TEST_DISH_2, TEST_DISH_3, TEST_DISH_4));


    public static final MenuDto SECOND_RESTAURANT_CREATE_DTO = new MenuDto("60c1ec6082f1f45502f687c0", LocalDate.of(2021, 12, 1),
            List.of(TEST_DISH_5, TEST_DISH_9, TEST_DISH_7));
    public static final MenuDto FIRST_RESTAURANT_UPDATE_DTO = new MenuDto(FIRST_RESTAURANT_FIRST_MENU.getId(), LocalDate.of(2021, 06, 9),
            List.of(TEST_DISH_5, TEST_DISH_6, TEST_DISH_7));

    public static final String REMOVE_MESSAGE = "The menu with ID = %s and a restaurant ID = %d was successfully removed";
}
