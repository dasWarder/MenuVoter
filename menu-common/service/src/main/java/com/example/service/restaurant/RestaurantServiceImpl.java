package com.example.service.restaurant;


import com.example.RestaurantRepository;
import com.example.exception.EntityNotFoundException;
import com.example.restaurant.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.util.OptionalValidation.checkOptionalAndReturnOrThrowException;
import static com.example.util.ParamValidationUtil.validateParametersNotNull;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {

        log.info("Receiving the collection of restaurants");

        return (List<Restaurant>) restaurantRepository.findAll();
    }

    @Override
    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurantToSave) {

        validateParametersNotNull(restaurantToSave);
        log.info("Storing the restaurant with the name = {}",
                                                             restaurantToSave.getName());
        return restaurantRepository.save(restaurantToSave);
    }

    @Override
    @Transactional
    public void deleteRestaurantById(Long restaurantId) {

        validateParametersNotNull(restaurantId);
        log.info("Removing the restaurant with ID = {}",
                                                        restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) throws EntityNotFoundException {

        validateParametersNotNull(restaurantId);

        Optional<Restaurant> possibleRestaurant = restaurantRepository.findById(restaurantId);
        Restaurant restaurantFromDB = checkOptionalAndReturnOrThrowException(possibleRestaurant, Restaurant.class);

        return restaurantFromDB;
    }

    @Override
    public Restaurant getRestaurantByName(String name) throws EntityNotFoundException {

        validateParametersNotNull(name);

        Optional<Restaurant> possibleRestaurant = restaurantRepository.getRestaurantByName(name);
        Restaurant restaurantFromDB = checkOptionalAndReturnOrThrowException(possibleRestaurant, Restaurant.class);

        return restaurantFromDB;
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(Long restaurantId, Restaurant restaurant) {

        validateParametersNotNull(restaurantId, restaurant);
        log.info("Update the restaurant with ID = {}",
                                                     restaurantId);
        restaurant.setId(restaurantId);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return updatedRestaurant;
    }
}
