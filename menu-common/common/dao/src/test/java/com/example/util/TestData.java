package com.example.util;

import com.example.customer.Customer;
import com.example.menu.Dish;
import com.example.menu.Menu;
import com.example.restaurant.Restaurant;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static final Restaurant TEST_RESTAURANT = new Restaurant(1L, "Wasabi", "The Wasabi restaurant");

    public static final Customer TEST_CUSTOMER = new Customer(1L, "alex@gmail.com", true);
    public static final Customer TEST_CUSTOMER_2 = new Customer(2L, "jack@gmail.com", true);
    public static final Customer TEST_CUSTOMER_3 = new Customer(3L, "david@gmail.com", false);
    public static final List<Customer> VOTED_CUSTOMERS = List.of(TEST_CUSTOMER, TEST_CUSTOMER_2);

    public static final Dish DISH_1 = new Dish("Ratatui", "The ratatui");
    public static final Dish DISH_2 = new Dish("Coffee", "The coffee");
    public static final Dish DISH_3 = new Dish("Soup", "The soup");
    public static final List<Dish> DISHES = List.of(DISH_1, DISH_2, DISH_3);

    public static final Menu TEST_MENU = new Menu(
            "abcd12345", 1L, 6.5d, 5L, LocalDate.now(), DISHES);

    public static final Menu TEST_MENU_2 = new Menu(
            "12345abcd", 2L, 7.0d, 5L, LocalDate.now(), DISHES);

    public static final Menu TEST_MENU_3 = new Menu(
            "12345678a", 1L, 5.5d, 4L, LocalDate.now(), List.of(DISH_1));

    public static final List<Menu> FIRST_RESTAURANT_MENUS = List.of(TEST_MENU, TEST_MENU_3);


}
