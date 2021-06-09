package com.example.service.util;

import com.example.restaurant.Restaurant;

import java.util.List;

public class TestData {

    public static final Restaurant TEST_RESTAURANT_2 = new Restaurant(2L, "Wasabi", "The wasabi restaurant");
    public static final Restaurant TEST_RESTAURANT = new Restaurant(1L, "Milano", "The milano restaurant");
    public static final Restaurant TEST_RESTAURANT_3 = new Restaurant(3L, "BuritoBar", "The Burito Bar");
    public static final Restaurant TEST_UPDATING_RESTAURANT = new Restaurant(null, "Updating", "Updating");
    public static final Restaurant UPDATED_RESTAURANT = new Restaurant(1L, "Updating", "Updating");
    public static final List<Restaurant> RESTAURANTS = List.of(TEST_RESTAURANT, TEST_RESTAURANT_2);
    public static final String WRONG_NAME = "Wrong";
    public static final Long WRONG_ID = 100L;
}
