package com.example.service.restaurant;

import com.example.restaurant.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    Restaurant save(Restaurant restaurant);

    void delete(Long restaurantId);

    Restaurant getById(Long restaurantId);

    Restaurant getByName(String name);

    Restaurant update(Long restaurantId, Restaurant restaurant);
}
