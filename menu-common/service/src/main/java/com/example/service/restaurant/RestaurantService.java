package com.example.service.restaurant;

import com.example.restaurant.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAllRestaurants();

    Restaurant saveRestaurant(Restaurant restaurantToSave);

    void deleteRestaurantById(Long restaurantId);

    Restaurant getRestaurantById(Long restaurantId);

    Restaurant getRestaurantByName(String name);

    Restaurant updateRestaurant(Long restaurantId, Restaurant restaurantForUpdating);
}
