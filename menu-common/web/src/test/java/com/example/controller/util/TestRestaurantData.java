package com.example.controller.util;

import com.example.restaurant.Restaurant;
import com.example.validation.exception.ExceptionResponse;
import com.example.validation.violation.Violation;

import static com.example.controller.util.TestData.WRONG_ID;

public class TestRestaurantData {

    //Name param for testing getRestaurantByName
    public static final String NAME_PARAM = "name";

    //Restaurants test data to testing controller
    public static final Restaurant FIRST_RESTAURANT = new Restaurant(1L, "Monaco", "The lux restaurant of Italian cousins");
    public static final Restaurant SECOND_RESTAURANT = new Restaurant(2L, "Wasabi", "The restaurant of Japanese and European cousins");
    public static final Restaurant[] MAIN_PAGE_RESTAURANTS = new Restaurant[]{ FIRST_RESTAURANT, SECOND_RESTAURANT };

    //Restaurants for testing CREATE and UPDATE methods
    public static final Restaurant CREATE_RESTAURANT = new Restaurant(1L, "Buffalo", "The best steak house");
    public static final Restaurant UPDATE_RESTAURANT = new Restaurant(null, "Updated", "Updated description");
    public static final Restaurant UPDATED_RESTAURANT = new Restaurant(CREATE_RESTAURANT.getId(), "Updated", "Updated description");

    //Restaurants with wrong data for testing validation
    public static final Restaurant WRONG_DESCRIPTION_RESTAURANT = new Restaurant(3L, "Wrong restaurant", "Test");
    public static final Restaurant EMPTY_NAME_RESTAURANT = new Restaurant(3L, "", "Restaurant description");

    //String as a body for ResponseEntity
    public static final String DELETE_MESSAGE = "The restaurant with ID = " + UPDATED_RESTAURANT.getId() + " was successfully removed";

   //Validation test data for exceptions (service layer)
    public static final ExceptionResponse RESTAURANT_NOT_FOUND = new ExceptionResponse("class com.example.exception.RestaurantNotFoundException",
            String.format("The restaurant with ID = %d not founded", WRONG_ID));

   //Validation test data for violation (controller)
    public static final Violation WRONG_RESTAURANT_DESCRIPTION = new Violation("description", "The description size must be  between 10 and 255");
    public static final Violation EMPTY_RESTAURANT_TITLE = new Violation("name", "The size of title must be between 1 and 60");
    public static final Violation MANDATORY_RESTAURANT_TITLE = new Violation("name", "The title is mandatory");
    public static final Violation GET_EMPTY_TITLE_RESTAURANT = new Violation("getRestaurantByName.name", "The name must be not empty");
    public static final Violation GET_EMPTY_TITLE_RESTAURANT_VALIDATION = new Violation("getRestaurantByName.name", "The name must be between 1 and 60");
}
