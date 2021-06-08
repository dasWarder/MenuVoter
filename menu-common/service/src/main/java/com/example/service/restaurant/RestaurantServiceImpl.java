package com.example.service.restaurant;


import com.example.RestaurantRepository;
import com.example.restaurant.Restaurant;
import com.example.exception.RestaurantNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAll() {
        log.info("Receiving the collection of restaurants");
        return (List<Restaurant>) restaurantRepository.findAll();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        notNull(restaurant, "The restaurant for storing must be NOT null");

        log.info("Storing the restaurant with ID = ", restaurant.getId());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(long restaurantId) {
        notNull(restaurantId, "The ID of the restaurant must be NOT null");

        log.info("Removing the restaurant with ID = ", restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public Restaurant getById(long restaurantId) {
        notNull(restaurantId, "The ID of the restaurant must be NOT null");

        Optional<Restaurant> possibleRestaurant = restaurantRepository.findById(restaurantId);

        if(possibleRestaurant.isPresent()) {
            log.info("Receiving the restaurant by ID = ", restaurantId);
            return possibleRestaurant.get();
        }

        log.info("The exception for the restaurant with ID = {} has been occurred", restaurantId);
        throw new RestaurantNotFoundException(String
                .format("The restaurant with ID = %d not founded", restaurantId));
    }

    @Override
    public Restaurant getByName(String name) {
        notNull(name, "The name of the restaurant must be NOT null");

        Optional<Restaurant> possibleRestaurant = restaurantRepository.getRestaurantByName(name);

        if(possibleRestaurant.isPresent()) {
            log.info("Receiving the restaurant with the name = ", name);
            return possibleRestaurant.get();
        }

        log.info("The exception for the restaurant with the name = {} has been occurred", name);
        throw new RestaurantNotFoundException(String
                .format("The restaurant with the name = %s not founded", name));
    }

    @Override
    public Restaurant update(Long restaurantId, Restaurant restaurant) {
        notNull(restaurant, "The restaurant must be not null");
        notNull(restaurantId, "The ID must be not null");

        log.info("Update the restaurant with ID = ", restaurantId);
        restaurant.setId(restaurantId);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return updatedRestaurant;
    }
}
