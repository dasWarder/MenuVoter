package com.example.restaurant;

import com.example.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    Restaurant save(Restaurant restaurant);

    void delete(long restaurantId);

    Restaurant getById(long restaurantId);

    Restaurant getByName(String name);

    Restaurant update(Long restaurantId, Restaurant restaurant);
}
