package com.example.service;

import com.example.customer.Customer;
import com.example.dto.MenuDto;
import com.example.dto.MenuRatedDto;
import com.example.menu.Dish;
import com.example.menu.Menu;
import com.example.restaurant.Restaurant;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static final Long WRONG_ID = 100L;

    public static final Restaurant TEST_RESTAURANT_2 = new Restaurant(2L, "Wasabi", "The wasabi restaurant");
    public static final Restaurant TEST_RESTAURANT = new Restaurant(1L, "Milano", "The milano restaurant");
    public static final Restaurant TEST_RESTAURANT_3 = new Restaurant(3L, "BuritoBar", "The Burito Bar");
    public static final Restaurant TEST_UPDATING_RESTAURANT = new Restaurant(null, "Updating", "Updating");
    public static final Restaurant UPDATED_RESTAURANT = new Restaurant(1L, "Updating", "Updating");
    public static final List<Restaurant> RESTAURANTS = List.of(TEST_RESTAURANT, TEST_RESTAURANT_2);
    public static final String WRONG_NAME = "Wrong";


    public static final Customer TEST_CUSTOMER_NO_ID = new Customer(null, "alex@gmail.com", true);
    public static final Customer TEST_CUSTOMER_WITH_ID = new Customer(1L, "alex@gmail.com", false);
    public static final Customer TEST_CUSTOMER_2 = new Customer(2L, "bill@gmail.com", true);
    public static final Customer TEST_CUSTOMER_3 = new Customer(3L, "clark@gmail.com", true);
    public static final Customer TEST_UPDATING_CUSTOMER = new Customer(null, "updating@gmail.com", false);
    public static final Customer UPDATED_CUSTOMER = new Customer(TEST_CUSTOMER_2.getId(), "updating@gmail.com", false);
    public static final List<Customer> ALL_CUSTOMERS = List.of(TEST_CUSTOMER_WITH_ID, TEST_CUSTOMER_2, TEST_CUSTOMER_3);
    public static final List<Customer> VOTED_CUSTOMERS = List.of(TEST_CUSTOMER_2, TEST_CUSTOMER_3);
    public static final String WRONG_EMAIL = "Wrong mail";

    public static final Dish TEST_DISH = new Dish("First", "First text");
    public static final Dish TEST_DISH_2 = new Dish("Second", "Second text");
    public static final Dish TEST_DISH_3 = new Dish("Third", "Third text");
    public static final Dish TEST_DISH_4 = new Dish("Fourth", "Fourth text");
    public static final Dish TEST_DISH_5 = new Dish("Fifth", "Fifth text");
    public static final List<Dish> TEST_FIRST_DISHES_LIST = List.of(TEST_DISH, TEST_DISH_2);
    public static final List<Dish> TEST_SECOND_DISHES_LIST = List.of(TEST_DISH_3, TEST_DISH_4);

    public static final MenuDto TEST_MENU_DTO = new MenuDto("a1", LocalDate.now(), TEST_FIRST_DISHES_LIST);
    public static final MenuDto TEST_MENU_DTO_WITH_NULL_ID = new MenuDto(null, LocalDate.now(), TEST_SECOND_DISHES_LIST);

    public static final Menu TEST_MENU = new Menu("a1", TEST_RESTAURANT.getId(), 0.0, 0L, LocalDate.now(), TEST_FIRST_DISHES_LIST);
    public static final Menu TEST_MENU_WITH_NULL_ID = new Menu(null, TEST_RESTAURANT.getId(), 0.0, 0L, LocalDate.now(), TEST_SECOND_DISHES_LIST);
    public static final Menu TEST_MENU_WITH_RATE = new Menu("a1", 2L, 4.3, 10L, LocalDate.now(), TEST_SECOND_DISHES_LIST);


    public static final MenuRatedDto TEST_RATED_DTO = new MenuRatedDto(
            TEST_MENU_WITH_RATE.getId(), (double) Math.round(TEST_MENU_WITH_RATE.getRate()), TEST_MENU_WITH_RATE.getVotesCount(),
            TEST_MENU_WITH_RATE.getCreatingDate(), TEST_MENU_WITH_RATE.getDishes());
    public static final MenuRatedDto TEST_RATED_DTO_2 = new MenuRatedDto(
            TEST_MENU.getId(), (double) Math.round(TEST_MENU.getRate()), TEST_MENU.getVotesCount(), TEST_MENU.getCreatingDate(),
            TEST_MENU.getDishes());
}
