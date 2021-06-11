package com.example;

import com.example.restaurant.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;



public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Optional<Restaurant> getRestaurantByName(String name);
}
