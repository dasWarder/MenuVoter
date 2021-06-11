package com.example.controller.util;

import com.example.dto.MenuDto;
import com.example.menu.Dish;
import com.example.menu.Menu;
import com.example.restaurant.Restaurant;
import com.example.validation.exception.ExceptionAnswer;
import com.example.validation.violation.Violation;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    //wrong IDs to test RESTAURANTS and MENUS
    public static final Long WRONG_ID = 12345L;
    public static final String WRONG_ID_STRING = "213q4retyguj";

    //Dishes for testing menu
    public static final Dish TEST_DISH = new Dish("Risotto", "Classic Italian risotto");
    public static final Dish TEST_DISH_2 = new Dish("Pizza margarete", "Classic Italian pizza");
    public static final Dish TEST_DISH_3 = new Dish("Pasta with seafood", "The best past in this town");
    public static final Dish TEST_DISH_4 = new Dish("Cappuccino", "A cup of cold cappuccino with ice");
    public static final Dish TEST_DISH_5 = new Dish("Miso soup", "The classic japanese miso soup");
    public static final Dish TEST_DISH_6 = new Dish("Yakitori", "The traditional japanese cousine");
    public static final Dish TEST_DISH_7 = new Dish("Matcha", "A coup of matcha tea");
    public static final Dish TEST_DISH_9 = new Dish("Omuraisu", "A typical omuraisu");
}
