package com.example.controller;

import com.example.restaurant.Restaurant;

public class TestData {
    public static final String NAME_PARAM = "name";
    public static final Restaurant FIRST_RESTAURANT = new Restaurant(
            1L, "Monaco", "The lux restaurant of Italian cousins");
    public static final Restaurant SECOND_RESTAURANT = new Restaurant(
            2L, "Wasabi", "The restaurant of Japanese and European cousins");
    public static final Restaurant[] MAIN_PAGE_RESTAURANTS = new Restaurant[]{ FIRST_RESTAURANT, SECOND_RESTAURANT };
    public static final Restaurant CREATE_RESTAURANT = new Restaurant(3L, "Buffalo", "The best steak house");
    public static final Restaurant UPDATE_RESTAURANT = new Restaurant(null, "Updated", "Updated");
    public static final Restaurant UPDATED_RESTAURANT = new Restaurant(CREATE_RESTAURANT.getId(), "Updated", "Updated");
    public static final String DELETE_MESSAGE = "The restaurant with ID = " + UPDATED_RESTAURANT.getId() + " was successfully removed";
}
