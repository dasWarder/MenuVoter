package com.example.service.restaurant;

import com.example.exception.EntityNotFoundException;
import com.example.exception.RestaurantNotFoundException;
import com.example.restaurant.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAllRestaurants();

    Restaurant saveRestaurant(Restaurant restaurantToSave);

    void deleteRestaurantById(Long restaurantId);

    Restaurant getRestaurantById(Long restaurantId) throws EntityNotFoundException;

    Restaurant getRestaurantByName(String name) throws EntityNotFoundException;

    Restaurant updateRestaurant(Long restaurantId, Restaurant restaurantForUpdating);
}
